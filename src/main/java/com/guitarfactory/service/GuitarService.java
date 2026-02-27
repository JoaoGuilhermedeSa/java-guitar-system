package com.guitarfactory.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.guitarfactory.domain.dto.request.GuitarOrderRequest;
import com.guitarfactory.domain.dto.response.GuitarResponse;
import com.guitarfactory.domain.entity.Guitar;
import com.guitarfactory.domain.entity.GuitarModel;
import com.guitarfactory.domain.entity.GuitarSpec;
import com.guitarfactory.domain.entity.PriceAdjustment;
import com.guitarfactory.domain.enums.BuildStatus;
import com.guitarfactory.domain.enums.GuitarOS;
import com.guitarfactory.exception.BusinessRuleException;
import com.guitarfactory.exception.ResourceNotFoundException;
import com.guitarfactory.mapper.GuitarMapper;
import com.guitarfactory.mapper.GuitarSpecMapper;
import com.guitarfactory.repository.GuitarModelRepository;
import com.guitarfactory.repository.GuitarRepository;
import com.guitarfactory.repository.PriceAdjustmentRepository;

@Service
public class GuitarService {
	
    private static final String EXTRA_STRING_FEE = "EXTRA_STRING_FEE";

    private final GuitarRepository repository;
    private final GuitarModelRepository modelRepository;
    private final GuitarMapper mapper;
    private final GuitarSpecMapper specMapper;
    private final PriceAdjustmentRepository priceAdjustmentRepository;

    public GuitarService(GuitarRepository repository,
                          GuitarModelRepository modelRepository,
                          GuitarMapper mapper,
                          GuitarSpecMapper specMapper,
                          com.guitarfactory.repository.PriceAdjustmentRepository priceAdjustmentRepository) {
        this.repository = repository;
        this.modelRepository = modelRepository;
        this.mapper = mapper;
        this.specMapper = specMapper;
        this.priceAdjustmentRepository = priceAdjustmentRepository;
    }

    public GuitarResponse placeOrder(GuitarOrderRequest request) {
        GuitarModel model = modelRepository.findById(request.modelId())
                .orElseThrow(() -> new ResourceNotFoundException("GuitarModel not found with id: " + request.modelId()));
        if (Boolean.FALSE.equals(model.getActive())) {
            throw new BusinessRuleException("Cannot order an inactive guitar model");
        }

        GuitarOS outputStyle = request.outputStyle() != null ? request.outputStyle() : model.getOutputStyle();
        GuitarSpec spec = request.customSpec() != null
                ? specMapper.toEntity(request.customSpec())
                : model.getDefaultSpec();

        BigDecimal finalPrice = calculateFinalPrice(model, spec);

        Guitar guitar = new Guitar();
        guitar.setModel(model);
        guitar.setOutputStyle(outputStyle);
        guitar.setSpec(spec);
        guitar.setFinalPrice(finalPrice);
        guitar.setCustomerName(request.customerName());
        guitar.setCustomerEmail(request.customerEmail());

        guitar = repository.save(guitar);

        guitar.setSerialNumber("GF-" + Year.now() + "-" + String.format("%06d", guitar.getId()));
        guitar = repository.save(guitar);

        return mapper.toResponse(guitar);
    }

    public GuitarResponse findById(Long id) {
        return mapper.toResponse(getOrThrow(id));
    }

    public List<GuitarResponse> findAll(BuildStatus status, Long modelId, String email) {
        if (status != null) {
            return repository.findByBuildStatus(status).stream().map(mapper::toResponse).toList();
        }
        if (modelId != null) {
            return repository.findByModelId(modelId).stream().map(mapper::toResponse).toList();
        }
        if (email != null) {
            return repository.findByCustomerEmail(email).stream().map(mapper::toResponse).toList();
        }
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    public GuitarResponse findBySerial(String serialNumber) {
        return repository.findBySerialNumber(serialNumber)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Guitar not found with serial: " + serialNumber));
    }

    public GuitarResponse advanceStatus(Long id) {
        Guitar guitar = getOrThrow(id);
        BuildStatus next = switch (guitar.getBuildStatus()) {
            case ORDERED -> BuildStatus.IN_PROGRESS;
            case IN_PROGRESS -> BuildStatus.QUALITY_CHECK;
            case QUALITY_CHECK -> BuildStatus.COMPLETED;
            case COMPLETED -> throw new BusinessRuleException("Guitar is already COMPLETED and cannot be advanced");
            case CANCELLED -> throw new BusinessRuleException("Cancelled guitars cannot be advanced");
        };
        guitar.setBuildStatus(next);
        if (next == BuildStatus.COMPLETED) {
            guitar.setCompletedAt(LocalDateTime.now());
        }
        return mapper.toResponse(repository.save(guitar));
    }

    public GuitarResponse cancelOrder(Long id) {
        Guitar guitar = getOrThrow(id);
        if (guitar.getBuildStatus() != BuildStatus.ORDERED
                && guitar.getBuildStatus() != BuildStatus.IN_PROGRESS) {
            throw new BusinessRuleException("Can only cancel orders in ORDERED or IN_PROGRESS status");
        }
        guitar.setBuildStatus(BuildStatus.CANCELLED);
        return mapper.toResponse(repository.save(guitar));
    }

    public void delete(Long id) {
        Guitar guitar = getOrThrow(id);
        if (guitar.getBuildStatus() != BuildStatus.CANCELLED) {
            throw new BusinessRuleException("Only CANCELLED guitars can be deleted");
        }
        repository.deleteById(id);
    }


    private BigDecimal calculateFinalPrice(GuitarModel model, GuitarSpec spec) {

        BigDecimal basePrice = model.getBasePrice();

        List<String> itemKeys = Stream.of(
                        spec.getBridgeType(),
                        spec.getBodyWood(),
                        spec.getFinishType(),
                        spec.getNeckPickup(),
                        spec.getBridgePickup()
                )
                .filter(Objects::nonNull)
                .map(Enum::name)
                .collect(Collectors.toCollection(ArrayList::new));

        itemKeys.add(EXTRA_STRING_FEE);

        Map<String, BigDecimal> adjustments =
                priceAdjustmentRepository.findByItemKeyIn(itemKeys)
                        .stream()
                        .collect(Collectors.toMap(
                                PriceAdjustment::getItemKey,
                                PriceAdjustment::getAdjustmentPrice,
                                (existing, _) -> existing
                        ));

        BigDecimal specAdjustmentsTotal = itemKeys.stream()
                .filter(key -> !EXTRA_STRING_FEE.equals(key))
                .map(key -> adjustments.getOrDefault(key, BigDecimal.ZERO))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal extraStringsAdjustment = calculateExtraStringsAdjustment(spec, adjustments);

        return basePrice
                .add(specAdjustmentsTotal)
                .add(extraStringsAdjustment);
    }

    private BigDecimal calculateExtraStringsAdjustment(
            GuitarSpec spec,
            Map<String, BigDecimal> adjustments
    ) {
        int extraStrings = spec.getNumberOfStrings() - 6;

        if (extraStrings <= 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal feePerString =
                adjustments.getOrDefault(EXTRA_STRING_FEE, BigDecimal.ZERO);

        return feePerString.multiply(BigDecimal.valueOf(extraStrings));
    }

    private Guitar getOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guitar not found with id: " + id));
    }
}
