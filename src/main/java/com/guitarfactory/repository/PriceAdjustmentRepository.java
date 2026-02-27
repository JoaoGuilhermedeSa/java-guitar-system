package com.guitarfactory.repository;

import java.util.List;
import java.util.Optional;

import com.guitarfactory.domain.entity.PriceAdjustment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceAdjustmentRepository extends JpaRepository<PriceAdjustment, Long> {
    Optional<PriceAdjustment> findByItemKey(String itemKey);
    List<PriceAdjustment> findByItemKeyIn(List<String> itemKeys);
}
