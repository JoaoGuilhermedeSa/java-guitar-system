package com.guitarfactory.service;

import com.guitarfactory.domain.dto.request.GuitarModelRequest;
import com.guitarfactory.domain.dto.response.GuitarModelResponse;
import com.guitarfactory.domain.entity.GuitarModel;
import com.guitarfactory.domain.enums.BuildStatus;
import com.guitarfactory.domain.enums.GuitarOS;
import com.guitarfactory.exception.BusinessRuleException;
import com.guitarfactory.exception.ResourceNotFoundException;
import com.guitarfactory.mapper.GuitarModelMapper;
import com.guitarfactory.repository.GuitarModelRepository;
import com.guitarfactory.repository.GuitarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuitarModelService {

    private final GuitarModelRepository repository;
    private final GuitarRepository guitarRepository;
    private final GuitarModelMapper mapper;

    public GuitarModelService(GuitarModelRepository repository,
                               GuitarRepository guitarRepository,
                               GuitarModelMapper mapper) {
        this.repository = repository;
        this.guitarRepository = guitarRepository;
        this.mapper = mapper;
    }

    public GuitarModelResponse create(GuitarModelRequest request) {
        if (repository.findByName(request.name()).isPresent()) {
            throw new BusinessRuleException("A model with name '" + request.name() + "' already exists");
        }
        return mapper.toResponse(repository.save(mapper.toEntity(request)));
    }

    public GuitarModelResponse findById(Long id) {
        return mapper.toResponse(getOrThrow(id));
    }

    public GuitarModelResponse findByName(String name) {
        return repository.findByName(name)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("GuitarModel not found with name: " + name));
    }

    public List<GuitarModelResponse> findAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    public List<GuitarModelResponse> findActive() {
        return repository.findByActiveTrue().stream().map(mapper::toResponse).toList();
    }

    public List<GuitarModelResponse> findByOutputStyle(GuitarOS style) {
        return repository.findByOutputStyle(style).stream().map(mapper::toResponse).toList();
    }

    public GuitarModelResponse update(Long id, GuitarModelRequest request) {
        GuitarModel model = getOrThrow(id);
        repository.findByName(request.name())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new BusinessRuleException("A model with name '" + request.name() + "' already exists");
                });
        model.setName(request.name());
        model.setDescription(request.description());
        model.setOutputStyle(request.outputStyle());
        model.setBasePrice(request.basePrice());
        return mapper.toResponse(repository.save(model));
    }

    public GuitarModelResponse deactivate(Long id) {
        GuitarModel model = getOrThrow(id);
        model.setActive(false);
        return mapper.toResponse(repository.save(model));
    }

    public void delete(Long id) {
        getOrThrow(id);
        boolean hasActiveGuitars = guitarRepository.findByModelId(id).stream()
                .anyMatch(g -> g.getBuildStatus() != BuildStatus.CANCELLED);
        if (hasActiveGuitars) {
            throw new BusinessRuleException("Cannot delete model with non-cancelled guitars referencing it");
        }
        repository.deleteById(id);
    }

    private GuitarModel getOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GuitarModel not found with id: " + id));
    }
}
