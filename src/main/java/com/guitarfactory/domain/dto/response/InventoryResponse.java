package com.guitarfactory.domain.dto.response;

import java.time.LocalDateTime;

public record InventoryResponse(
        Long id,
        GuitarModelResponse model,
        GuitarSpecResponse spec,
        Integer quantityAvailable,
        Integer reorderThreshold,
        LocalDateTime lastUpdated
) {}
