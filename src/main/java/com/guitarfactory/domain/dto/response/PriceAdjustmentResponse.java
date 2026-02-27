package com.guitarfactory.domain.dto.response;

import java.math.BigDecimal;

public record PriceAdjustmentResponse(
    Long id,
    String itemKey,
    String description,
    BigDecimal adjustmentPrice
) {}
