package com.guitarfactory.repository;

import com.guitarfactory.domain.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByModelIdAndSpecId(Long modelId, Long specId);

    List<Inventory> findByModelId(Long modelId);

    @Query("SELECT i FROM Inventory i WHERE i.quantityAvailable <= i.reorderThreshold")
    List<Inventory> findLowStock();
}
