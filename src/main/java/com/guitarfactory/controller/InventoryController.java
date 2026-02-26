package com.guitarfactory.controller;

import com.guitarfactory.domain.dto.request.InventoryAdjustRequest;
import com.guitarfactory.domain.dto.response.InventoryResponse;
import com.guitarfactory.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    private final InventoryService service;

    public InventoryController(InventoryService service) {
        this.service = service;
    }

    @PostMapping
    public InventoryResponse upsert(@Valid @RequestBody InventoryAdjustRequest request) {
        return service.upsert(request);
    }

    @GetMapping
    public List<InventoryResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public InventoryResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/model/{modelId}")
    public List<InventoryResponse> findByModelId(@PathVariable Long modelId) {
        return service.findByModelId(modelId);
    }

    @GetMapping("/low-stock")
    public List<InventoryResponse> findLowStock() {
        return service.findLowStock();
    }

    @PatchMapping("/{id}/adjust")
    public InventoryResponse adjustQuantity(@PathVariable Long id,
                                             @RequestBody Map<String, Integer> body) {
        return service.adjustQuantity(id, body.get("delta"));
    }
}
