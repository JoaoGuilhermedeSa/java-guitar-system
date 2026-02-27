package com.guitarfactory.domain.dto.request;

import java.math.BigDecimal;

public record PriceAdjustmentRequest(
    String itemKey,
    String description,
    BigDecimal adjustmentPrice
) {}
