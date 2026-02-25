package com.guitarfactory.domain.dto.request;

import com.guitarfactory.domain.enums.BridgeType;
import com.guitarfactory.domain.enums.FinishType;
import com.guitarfactory.domain.enums.PickupType;
import com.guitarfactory.domain.enums.WoodType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GuitarSpecRequest(
        @NotNull @Min(4) @Max(12) Integer numberOfStrings,
        @NotNull @Min(18) @Max(30) Integer numberOfFrets,
        @NotNull Double scaleLengthInches,
        @NotNull WoodType bodyWood,
        @NotNull WoodType neckWood,
        @NotNull WoodType fretboardWood,
        @NotNull PickupType neckPickup,
        @NotNull PickupType bridgePickup,
        @NotNull BridgeType bridgeType,
        @NotNull FinishType finishType,
        @NotBlank String color
) {}
