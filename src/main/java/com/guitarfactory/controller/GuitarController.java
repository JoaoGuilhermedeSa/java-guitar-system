package com.guitarfactory.controller;

import com.guitarfactory.domain.dto.request.GuitarOrderRequest;
import com.guitarfactory.domain.dto.response.GuitarResponse;
import com.guitarfactory.domain.enums.BuildStatus;
import com.guitarfactory.service.GuitarService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/guitars")
public class GuitarController {

    private final GuitarService service;

    public GuitarController(GuitarService service) {
        this.service = service;
    }

    @PostMapping("/order")
    @ResponseStatus(HttpStatus.CREATED)
    public GuitarResponse placeOrder(@Valid @RequestBody GuitarOrderRequest request) {
        return service.placeOrder(request);
    }

    @GetMapping
    public List<GuitarResponse> findAll(
            @RequestParam(required = false) BuildStatus status,
            @RequestParam(required = false) Long modelId,
            @RequestParam(required = false) String email) {
        return service.findAll(status, modelId, email);
    }

    @GetMapping("/{id}")
    public GuitarResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/serial/{serialNumber}")
    public GuitarResponse findBySerial(@PathVariable String serialNumber) {
        return service.findBySerial(serialNumber);
    }

    @PatchMapping("/{id}/advance")
    public GuitarResponse advanceStatus(@PathVariable Long id) {
        return service.advanceStatus(id);
    }

    @PatchMapping("/{id}/cancel")
    public GuitarResponse cancelOrder(@PathVariable Long id) {
        return service.cancelOrder(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
