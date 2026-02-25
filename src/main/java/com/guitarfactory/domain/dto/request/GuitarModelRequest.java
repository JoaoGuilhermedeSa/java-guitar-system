package com.guitarfactory.domain.dto.request;

import com.guitarfactory.domain.enums.GuitarOS;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record GuitarModelRequest(
        @NotBlank String name,
        String description,
        @NotNull GuitarOS outputStyle,
        @NotNull @Valid GuitarSpecRequest defaultSpec,
        @NotNull BigDecimal basePrice
) {}
