package com.guitarfactory.mapper;

import com.guitarfactory.domain.dto.response.InventoryResponse;
import com.guitarfactory.domain.entity.Inventory;
import org.springframework.stereotype.Component;

@Component
public class InventoryMapper {

    private final GuitarModelMapper guitarModelMapper;
    private final GuitarSpecMapper guitarSpecMapper;

    public InventoryMapper(GuitarModelMapper guitarModelMapper, GuitarSpecMapper guitarSpecMapper) {
        this.guitarModelMapper = guitarModelMapper;
        this.guitarSpecMapper = guitarSpecMapper;
    }

    public InventoryResponse toResponse(Inventory inventory) {
        return new InventoryResponse(
                inventory.getId(),
                guitarModelMapper.toResponse(inventory.getModel()),
                guitarSpecMapper.toResponse(inventory.getSpec()),
                inventory.getQuantityAvailable(),
                inventory.getReorderThreshold(),
                inventory.getLastUpdated()
        );
    }
}
