package com.guitarfactory.controller;

import com.guitarfactory.domain.dto.request.PriceAdjustmentRequest;
import com.guitarfactory.domain.dto.response.PriceAdjustmentResponse;
import com.guitarfactory.service.PriceAdjustmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pricing")
@Tag(name = "Pricing", description = "Endpoints for managing component surcharges")
public class PriceAdjustmentController {

    private final PriceAdjustmentService service;

    public PriceAdjustmentController(PriceAdjustmentService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get all component surcharges")
    public ResponseEntity<List<PriceAdjustmentResponse>> getAllAdjustments() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a surcharge by ID")
    public ResponseEntity<PriceAdjustmentResponse> getAdjustmentById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a surcharge amount")
    public ResponseEntity<PriceAdjustmentResponse> updateAdjustment(
            @PathVariable Long id, 
            @RequestBody PriceAdjustmentRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }
}
