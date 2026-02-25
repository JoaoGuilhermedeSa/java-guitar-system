package com.guitarfactory.domain.dto.response;

import com.guitarfactory.domain.enums.GuitarOS;

import java.math.BigDecimal;

public record GuitarModelResponse(
        Long id,
        String name,
        String description,
        GuitarOS outputStyle,
        GuitarSpecResponse defaultSpec,
        BigDecimal basePrice,
        Boolean active
) {}
