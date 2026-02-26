package com.guitarfactory.mapper;

import com.guitarfactory.domain.dto.request.GuitarModelRequest;
import com.guitarfactory.domain.dto.response.GuitarModelResponse;
import com.guitarfactory.domain.entity.GuitarModel;
import org.springframework.stereotype.Component;

@Component
public class GuitarModelMapper {

    private final GuitarSpecMapper guitarSpecMapper;

    public GuitarModelMapper(GuitarSpecMapper guitarSpecMapper) {
        this.guitarSpecMapper = guitarSpecMapper;
    }

    public GuitarModel toEntity(GuitarModelRequest request) {
        GuitarModel model = new GuitarModel();
        model.setName(request.name());
        model.setDescription(request.description());
        model.setOutputStyle(request.outputStyle());
        model.setDefaultSpec(guitarSpecMapper.toEntity(request.defaultSpec()));
        model.setBasePrice(request.basePrice());
        model.setActive(true);
        return model;
    }

    public GuitarModelResponse toResponse(GuitarModel model) {
        return new GuitarModelResponse(
                model.getId(),
                model.getName(),
                model.getDescription(),
                model.getOutputStyle(),
                guitarSpecMapper.toResponse(model.getDefaultSpec()),
                model.getBasePrice(),
                model.getActive()
        );
    }
}
