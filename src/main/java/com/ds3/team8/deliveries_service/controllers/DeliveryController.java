package com.ds3.team8.deliveries_service.controllers;

import com.ds3.team8.deliveries_service.dtos.DeliveryRequest;
import com.ds3.team8.deliveries_service.dtos.DeliveryResponse;
import com.ds3.team8.deliveries_service.services.IDeliveryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/delivery")
public class DeliveryController {

    private IDeliveryService deliveryService;

    public DeliveryController(IDeliveryService deliveryService){
        this.deliveryService = deliveryService;
    }
    // Obtener todos los deliveries
    @GetMapping
    public ResponseEntity<List<DeliveryResponse>> getAllDeliveries() {
        return ResponseEntity.ok(deliveryService.findAll());
    }

    // Obtener deliveries con paginación
    // Ejemplo URL: /api/v1/deliveries/pageable?page=0&size=8
    @GetMapping("/pageable")
    public ResponseEntity<Page<DeliveryResponse>> getAllDeliveries(Pageable pageable) {
        Page<DeliveryResponse> deliveries = deliveryService.findAll(pageable);
        return ResponseEntity.ok(deliveries);
    }

    // Obtener un delivery por ID
    @GetMapping("/{id}")
    public ResponseEntity<DeliveryResponse> getDeliveryById(@PathVariable Long id) {
        return ResponseEntity.ok(deliveryService.findById(id));
    }

    // Crear un nuevo delivery
    @PostMapping
    public ResponseEntity<DeliveryResponse> createDelivery(@Valid @RequestBody DeliveryRequest deliveryRequest) {
        DeliveryResponse savedDelivery = deliveryService.save(deliveryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDelivery);
    }

    // Actualizar un delivery existente
    @PutMapping("/{id}")
    public ResponseEntity<DeliveryResponse> updateDelivery(
            @PathVariable Long id,
            @Valid @RequestBody DeliveryRequest deliveryRequest) {
        DeliveryResponse updatedDelivery = deliveryService.update(id, deliveryRequest);
        return ResponseEntity.ok(updatedDelivery);
    }

    // Eliminación lógica de un delivery
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDelivery(@PathVariable Long id) {
        deliveryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
