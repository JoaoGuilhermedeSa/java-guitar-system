package com.guitarfactory.domain.dto.response;

import com.guitarfactory.domain.enums.BuildStatus;
import com.guitarfactory.domain.enums.GuitarOS;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record GuitarResponse(
        Long id,
        String serialNumber,
        GuitarModelResponse model,
        GuitarOS outputStyle,
        GuitarSpecResponse spec,
        BuildStatus buildStatus,
        BigDecimal finalPrice,
        String customerName,
        String customerEmail,
        LocalDateTime orderedAt,
        LocalDateTime completedAt
) {}
