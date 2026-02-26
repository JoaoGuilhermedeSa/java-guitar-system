package com.guitarfactory.mapper;

import com.guitarfactory.domain.dto.response.GuitarResponse;
import com.guitarfactory.domain.entity.Guitar;
import org.springframework.stereotype.Component;

@Component
public class GuitarMapper {

    private final GuitarModelMapper guitarModelMapper;
    private final GuitarSpecMapper guitarSpecMapper;

    public GuitarMapper(GuitarModelMapper guitarModelMapper, GuitarSpecMapper guitarSpecMapper) {
        this.guitarModelMapper = guitarModelMapper;
        this.guitarSpecMapper = guitarSpecMapper;
    }

    public GuitarResponse toResponse(Guitar guitar) {
        return new GuitarResponse(
                guitar.getId(),
                guitar.getSerialNumber(),
                guitarModelMapper.toResponse(guitar.getModel()),
                guitar.getOutputStyle(),
                guitarSpecMapper.toResponse(guitar.getSpec()),
                guitar.getBuildStatus(),
                guitar.getFinalPrice(),
                guitar.getCustomerName(),
                guitar.getCustomerEmail(),
                guitar.getOrderedAt(),
                guitar.getCompletedAt()
        );
    }
}
