package com.guitarfactory.repository;

import com.guitarfactory.domain.entity.GuitarModel;
import com.guitarfactory.domain.enums.GuitarOS;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GuitarModelRepository extends JpaRepository<GuitarModel, Long> {

    Optional<GuitarModel> findByName(String name);

    List<GuitarModel> findByOutputStyle(GuitarOS outputStyle);

    List<GuitarModel> findByActiveTrue();
}
