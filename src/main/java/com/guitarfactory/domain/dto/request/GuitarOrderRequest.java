package com.guitarfactory.domain.dto.request;

import com.guitarfactory.domain.enums.GuitarOS;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GuitarOrderRequest(
        @NotNull Long modelId,
        GuitarOS outputStyle,
        @Valid GuitarSpecRequest customSpec,
        @NotBlank String customerName,
        @NotBlank @Email String customerEmail
) {}
