package com.guitarfactory.mapper;

import com.guitarfactory.domain.dto.request.GuitarSpecRequest;
import com.guitarfactory.domain.dto.response.GuitarSpecResponse;
import com.guitarfactory.domain.entity.GuitarSpec;
import org.springframework.stereotype.Component;

@Component
public class GuitarSpecMapper {

    public GuitarSpec toEntity(GuitarSpecRequest request) {
        GuitarSpec spec = new GuitarSpec();
        spec.setNumberOfStrings(request.numberOfStrings());
        spec.setNumberOfFrets(request.numberOfFrets());
        spec.setScaleLengthInches(request.scaleLengthInches());
        spec.setBodyWood(request.bodyWood());
        spec.setNeckWood(request.neckWood());
        spec.setFretboardWood(request.fretboardWood());
        spec.setNeckPickup(request.neckPickup());
        spec.setBridgePickup(request.bridgePickup());
        spec.setBridgeType(request.bridgeType());
        spec.setFinishType(request.finishType());
        spec.setColor(request.color());
        return spec;
    }

    public GuitarSpecResponse toResponse(GuitarSpec spec) {
        return new GuitarSpecResponse(
                spec.getId(),
                spec.getNumberOfStrings(),
                spec.getNumberOfFrets(),
                spec.getScaleLengthInches(),
                spec.getBodyWood(),
                spec.getNeckWood(),
                spec.getFretboardWood(),
                spec.getNeckPickup(),
                spec.getBridgePickup(),
                spec.getBridgeType(),
                spec.getFinishType(),
                spec.getColor()
        );
    }
}
