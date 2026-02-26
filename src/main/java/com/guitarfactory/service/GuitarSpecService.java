package com.guitarfactory.service;

import com.guitarfactory.domain.dto.request.GuitarSpecRequest;
import com.guitarfactory.domain.dto.response.GuitarSpecResponse;
import com.guitarfactory.domain.entity.GuitarSpec;
import com.guitarfactory.exception.ResourceNotFoundException;
import com.guitarfactory.mapper.GuitarSpecMapper;
import com.guitarfactory.repository.GuitarSpecRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuitarSpecService {

    private final GuitarSpecRepository repository;
    private final GuitarSpecMapper mapper;

    public GuitarSpecService(GuitarSpecRepository repository, GuitarSpecMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public GuitarSpecResponse create(GuitarSpecRequest request) {
        return mapper.toResponse(repository.save(mapper.toEntity(request)));
    }

    public GuitarSpecResponse findById(Long id) {
        return mapper.toResponse(getOrThrow(id));
    }

    public List<GuitarSpecResponse> findAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    public GuitarSpecResponse update(Long id, GuitarSpecRequest request) {
        GuitarSpec spec = getOrThrow(id);
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
        return mapper.toResponse(repository.save(spec));
    }

    public void delete(Long id) {
        getOrThrow(id);
        repository.deleteById(id);
    }

    private GuitarSpec getOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GuitarSpec not found with id: " + id));
    }
}
