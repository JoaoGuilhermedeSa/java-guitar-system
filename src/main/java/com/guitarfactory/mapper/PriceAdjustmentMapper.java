package com.guitarfactory.mapper;

import com.guitarfactory.domain.dto.request.PriceAdjustmentRequest;
import com.guitarfactory.domain.dto.response.PriceAdjustmentResponse;
import com.guitarfactory.domain.entity.PriceAdjustment;
import org.springframework.stereotype.Component;

@Component
public class PriceAdjustmentMapper {

    public PriceAdjustment toEntity(PriceAdjustmentRequest request) {
        PriceAdjustment entity = new PriceAdjustment();
        entity.setItemKey(request.itemKey());
        entity.setDescription(request.description());
        entity.setAdjustmentPrice(request.adjustmentPrice());
        return entity;
    }

    public PriceAdjustmentResponse toResponse(PriceAdjustment entity) {
        return new PriceAdjustmentResponse(
            entity.getId(),
            entity.getItemKey(),
            entity.getDescription(),
            entity.getAdjustmentPrice()
        );
    }
}
