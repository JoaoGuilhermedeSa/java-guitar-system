package com.guitarfactory.domain.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record InventoryAdjustRequest(
        @NotNull Long modelId,
        @NotNull Long specId,
        @NotNull @Min(0) Integer quantityAvailable,
        @NotNull @Min(0) Integer reorderThreshold
) {}
