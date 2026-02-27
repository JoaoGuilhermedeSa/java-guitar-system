package com.guitarfactory.service;

import com.guitarfactory.domain.dto.request.PriceAdjustmentRequest;
import com.guitarfactory.domain.dto.response.PriceAdjustmentResponse;
import com.guitarfactory.domain.entity.PriceAdjustment;
import com.guitarfactory.exception.ResourceNotFoundException;
import com.guitarfactory.mapper.PriceAdjustmentMapper;
import com.guitarfactory.repository.PriceAdjustmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceAdjustmentService {

    private final PriceAdjustmentRepository repository;
    private final PriceAdjustmentMapper mapper;

    public PriceAdjustmentService(PriceAdjustmentRepository repository, PriceAdjustmentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<PriceAdjustmentResponse> findAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    public PriceAdjustmentResponse findById(Long id) {
        return mapper.toResponse(getOrThrow(id));
    }

    public PriceAdjustmentResponse update(Long id, PriceAdjustmentRequest request) {
        PriceAdjustment adjustment = getOrThrow(id);
        adjustment.setAdjustmentPrice(request.adjustmentPrice());
        adjustment.setDescription(request.description());
        // itemKey is typically not changed to avoid breaking existing logic
        return mapper.toResponse(repository.save(adjustment));
    }

    private PriceAdjustment getOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PriceAdjustment not found with id: " + id));
    }
}
