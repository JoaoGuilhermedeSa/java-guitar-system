package com.guitarfactory.service;

import com.guitarfactory.domain.dto.request.GuitarOrderRequest;
import com.guitarfactory.domain.dto.response.GuitarResponse;
import com.guitarfactory.domain.entity.Guitar;
import com.guitarfactory.domain.entity.GuitarModel;
import com.guitarfactory.domain.entity.GuitarSpec;
import com.guitarfactory.domain.enums.BridgeType;
import com.guitarfactory.domain.enums.BuildStatus;
import com.guitarfactory.domain.enums.FinishType;
import com.guitarfactory.domain.enums.GuitarOS;
import com.guitarfactory.domain.enums.PickupType;
import com.guitarfactory.domain.enums.WoodType;
import com.guitarfactory.exception.BusinessRuleException;
import com.guitarfactory.exception.ResourceNotFoundException;
import com.guitarfactory.mapper.GuitarMapper;
import com.guitarfactory.mapper.GuitarSpecMapper;
import com.guitarfactory.repository.GuitarModelRepository;
import com.guitarfactory.repository.GuitarRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;

@Service
public class GuitarService {

    private final GuitarRepository repository;
    private final GuitarModelRepository modelRepository;
    private final GuitarMapper mapper;
    private final GuitarSpecMapper specMapper;

    public GuitarService(GuitarRepository repository,
                          GuitarModelRepository modelRepository,
                          GuitarMapper mapper,
                          GuitarSpecMapper specMapper) {
        this.repository = repository;
        this.modelRepository = modelRepository;
        this.mapper = mapper;
        this.specMapper = specMapper;
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

        // First save to generate ID (@PrePersist sets status=ORDERED and orderedAt)
        guitar = repository.save(guitar);

        // Generate serial number using the assigned ID
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

    // Step 9 — price upcharge rules
    private BigDecimal calculateFinalPrice(GuitarModel model, GuitarSpec spec) {
        BigDecimal price = model.getBasePrice();

        if (spec.getBridgeType() == BridgeType.FLOYD_ROSE) {
            price = price.add(new BigDecimal("200.00"));
        } else if (spec.getBridgeType() == BridgeType.BIGSBY) {
            price = price.add(new BigDecimal("120.00"));
        }

        if (spec.getNeckPickup() == PickupType.ACTIVE_HUMBUCKER
                || spec.getBridgePickup() == PickupType.ACTIVE_HUMBUCKER) {
            price = price.add(new BigDecimal("150.00"));
        }

        int extraStrings = spec.getNumberOfStrings() - 6;
        if (extraStrings > 0) {
            price = price.add(new BigDecimal("100.00").multiply(new BigDecimal(extraStrings)));
        }

        if (spec.getBodyWood() == WoodType.KOA) {
            price = price.add(new BigDecimal("300.00"));
        }

        if (spec.getFinishType() == FinishType.NITROCELLULOSE_LACQUER) {
            price = price.add(new BigDecimal("175.00"));
        }

        return price;
    }

    private Guitar getOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guitar not found with id: " + id));
    }
}
