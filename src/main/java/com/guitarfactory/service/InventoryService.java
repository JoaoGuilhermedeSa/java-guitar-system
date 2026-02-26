package com.guitarfactory.service;

import com.guitarfactory.domain.dto.request.InventoryAdjustRequest;
import com.guitarfactory.domain.dto.response.InventoryResponse;
import com.guitarfactory.domain.entity.GuitarModel;
import com.guitarfactory.domain.entity.GuitarSpec;
import com.guitarfactory.domain.entity.Inventory;
import com.guitarfactory.exception.BusinessRuleException;
import com.guitarfactory.exception.ResourceNotFoundException;
import com.guitarfactory.mapper.InventoryMapper;
import com.guitarfactory.repository.GuitarModelRepository;
import com.guitarfactory.repository.GuitarSpecRepository;
import com.guitarfactory.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepository repository;
    private final GuitarModelRepository modelRepository;
    private final GuitarSpecRepository specRepository;
    private final InventoryMapper mapper;

    public InventoryService(InventoryRepository repository,
                             GuitarModelRepository modelRepository,
                             GuitarSpecRepository specRepository,
                             InventoryMapper mapper) {
        this.repository = repository;
        this.modelRepository = modelRepository;
        this.specRepository = specRepository;
        this.mapper = mapper;
    }

    public InventoryResponse upsert(InventoryAdjustRequest request) {
        GuitarModel model = modelRepository.findById(request.modelId())
                .orElseThrow(() -> new ResourceNotFoundException("GuitarModel not found with id: " + request.modelId()));
        GuitarSpec spec = specRepository.findById(request.specId())
                .orElseThrow(() -> new ResourceNotFoundException("GuitarSpec not found with id: " + request.specId()));

        Inventory inventory = repository.findByModelIdAndSpecId(request.modelId(), request.specId())
                .orElse(new Inventory());

        inventory.setModel(model);
        inventory.setSpec(spec);
        inventory.setQuantityAvailable(request.quantityAvailable());
        inventory.setReorderThreshold(request.reorderThreshold());
        return mapper.toResponse(repository.save(inventory));
    }

    public InventoryResponse findById(Long id) {
        return mapper.toResponse(getOrThrow(id));
    }

    public List<InventoryResponse> findAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    public List<InventoryResponse> findByModelId(Long modelId) {
        return repository.findByModelId(modelId).stream().map(mapper::toResponse).toList();
    }

    public List<InventoryResponse> findLowStock() {
        return repository.findLowStock().stream().map(mapper::toResponse).toList();
    }

    public InventoryResponse adjustQuantity(Long id, int delta) {
        Inventory inventory = getOrThrow(id);
        int newQty = inventory.getQuantityAvailable() + delta;
        if (newQty < 0) {
            throw new BusinessRuleException("Adjustment would result in negative inventory quantity");
        }
        inventory.setQuantityAvailable(newQty);
        return mapper.toResponse(repository.save(inventory));
    }

    private Inventory getOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found with id: " + id));
    }
}
