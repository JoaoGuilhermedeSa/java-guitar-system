package com.guitarfactory.controller;

import com.guitarfactory.domain.dto.request.GuitarSpecRequest;
import com.guitarfactory.domain.dto.response.GuitarSpecResponse;
import com.guitarfactory.service.GuitarSpecService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/specs")
public class GuitarSpecController {

    private final GuitarSpecService service;

    public GuitarSpecController(GuitarSpecService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GuitarSpecResponse create(@Valid @RequestBody GuitarSpecRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<GuitarSpecResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public GuitarSpecResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public GuitarSpecResponse update(@PathVariable Long id, @Valid @RequestBody GuitarSpecRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
