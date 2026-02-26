package com.guitarfactory.controller;

import com.guitarfactory.domain.dto.request.GuitarModelRequest;
import com.guitarfactory.domain.dto.response.GuitarModelResponse;
import com.guitarfactory.domain.enums.GuitarOS;
import com.guitarfactory.service.GuitarModelService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/models")
public class GuitarModelController {

    private final GuitarModelService service;

    public GuitarModelController(GuitarModelService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GuitarModelResponse create(@Valid @RequestBody GuitarModelRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<GuitarModelResponse> findAll(
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) GuitarOS style) {
        if (Boolean.TRUE.equals(active)) {
            return service.findActive();
        }
        if (style != null) {
            return service.findByOutputStyle(style);
        }
        return service.findAll();
    }

    @GetMapping("/{id}")
    public GuitarModelResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/name/{name}")
    public GuitarModelResponse findByName(@PathVariable String name) {
        return service.findByName(name);
    }

    @PutMapping("/{id}")
    public GuitarModelResponse update(@PathVariable Long id, @Valid @RequestBody GuitarModelRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/deactivate")
    public GuitarModelResponse deactivate(@PathVariable Long id) {
        return service.deactivate(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
