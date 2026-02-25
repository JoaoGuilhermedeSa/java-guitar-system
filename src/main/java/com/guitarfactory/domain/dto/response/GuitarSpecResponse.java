package com.guitarfactory.domain.dto.response;

import com.guitarfactory.domain.enums.BridgeType;
import com.guitarfactory.domain.enums.FinishType;
import com.guitarfactory.domain.enums.PickupType;
import com.guitarfactory.domain.enums.WoodType;

public record GuitarSpecResponse(
        Long id,
        Integer numberOfStrings,
        Integer numberOfFrets,
        Double scaleLengthInches,
        WoodType bodyWood,
        WoodType neckWood,
        WoodType fretboardWood,
        PickupType neckPickup,
        PickupType bridgePickup,
        BridgeType bridgeType,
        FinishType finishType,
        String color
) {}
