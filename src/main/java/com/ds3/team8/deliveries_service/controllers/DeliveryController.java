package com.ds3.team8.deliveries_service.controllers;

import com.ds3.team8.deliveries_service.client.enums.Role;
import com.ds3.team8.deliveries_service.dtos.DeliveryRequest;
import com.ds3.team8.deliveries_service.dtos.DeliveryResponse;
import com.ds3.team8.deliveries_service.dtos.DeliveryStatusRequest;
import com.ds3.team8.deliveries_service.services.IDeliveryService;
import com.ds3.team8.deliveries_service.utils.SecurityUtil;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/deliveries")
@Tag(name = "Entregas", description = "Endpoints para entregas")
public class DeliveryController {

    private final IDeliveryService deliveryService;

    public DeliveryController(IDeliveryService deliveryService){
        this.deliveryService = deliveryService;
    }

    // Obtener todos las entregas
    @Operation(summary = "Obtener todos las entregas", description = "Obtener todos las entregas del sistema.", security = { @SecurityRequirement(name = "Bearer Authentication") })
    @GetMapping
    public ResponseEntity<List<DeliveryResponse>> getAllDeliveries(
        @RequestHeader("X-Authenticated-User-Role") String roleHeader
    ) {
        SecurityUtil.validateRole(roleHeader, Role.ADMIN);
        return ResponseEntity.ok(deliveryService.findAll());
    }

    // Obtener entregas con paginación
    // Ejemplo URL /api/v1/deliveries/pageable?page=0&size=8
    @Operation(summary = "Obtener las entregas con paginación", description = "Obtener las entregas con paginación del sistema.", security = { @SecurityRequirement(name = "Bearer Authentication") })
    @GetMapping("/pageable")
    public ResponseEntity<Page<DeliveryResponse>> getAllDeliveries(
        Pageable pageable,
        @RequestHeader("X-Authenticated-User-Role") String roleHeader
    ) {
        SecurityUtil.validateRole(roleHeader, Role.ADMIN);
        Page<DeliveryResponse> deliveries = deliveryService.findAllPageable(pageable);
        return ResponseEntity.ok(deliveries);
    }

    // Obtener una entrega por ID
    @Operation(summary = "Obtener una entrega por su id", description = "Obtener una entrega por su id del sistema.", security = { @SecurityRequirement(name = "Bearer Authentication") })
    @GetMapping("/{id}")
    public ResponseEntity<DeliveryResponse> getDeliveryById(@PathVariable Long id) {
        return ResponseEntity.ok(deliveryService.findById(id));
    }

    // Guardar una nueva entrega
    @Operation(summary = "Guardar una entrega", description = "Guardar una entrega en el sistema.", security = { @SecurityRequirement(name = "Bearer Authentication") })
    @PostMapping
    public ResponseEntity<DeliveryResponse> createDelivery(
        @Valid @RequestBody DeliveryRequest deliveryRequest,
        @RequestHeader("X-Authenticated-User-Role") String roleHeader
    ) {
        SecurityUtil.validateRole(roleHeader, Role.ADMIN);
        DeliveryResponse savedDelivery = deliveryService.save(deliveryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDelivery);
    }

    // Actualizar una entrega
    @Operation(summary = "Actualizar una entrega", description = "Actualizar una entrega en el sistema.", security = { @SecurityRequirement(name = "Bearer Authentication") })
    @PutMapping("/{id}")
    public ResponseEntity<DeliveryResponse> updateDelivery(
            @PathVariable Long id,
            @Valid @RequestBody DeliveryRequest deliveryRequest,
            @RequestHeader("X-Authenticated-User-Role") String roleHeader
        ) {
        SecurityUtil.validateRole(roleHeader, Role.ADMIN);
        DeliveryResponse updatedDelivery = deliveryService.update(id, deliveryRequest);
        return ResponseEntity.ok(updatedDelivery);
    }

    // Eliminación lógica de una entrega
    @Operation(summary = "Eliminar una entrega", description = "Eliminar una entrega en el sistema.", security = { @SecurityRequirement(name = "Bearer Authentication") })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDelivery(
        @PathVariable Long id,
        @RequestHeader("X-Authenticated-User-Role") String roleHeader
    ) {
        SecurityUtil.validateRole(roleHeader, Role.ADMIN);
        deliveryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Obtener entregas por ID de usuario
    @Operation(summary = "Obtener entregas por ID de usuario", description = "Obtener entregas por ID de usuario del sistema.", security = { @SecurityRequirement(name = "Bearer Authentication") })
    @GetMapping("/user")
    public ResponseEntity<List<DeliveryResponse>> getDeliveriesByUserId(
        @RequestHeader("X-Authenticated-User-Id") String userIdHeader
    ) {
        Long userId = SecurityUtil.parseUserId(userIdHeader);
        return ResponseEntity.ok(deliveryService.findAllByDriverId(userId));
    }

    // Obtener entregas por ID de usuario con paginación
    // Ejemplo URL /api/v1/deliveries/user/pageable?page=0&size=8
    @Operation(summary = "Obtener entregas por ID de usuario con paginación", description = "Obtener entregas por ID de usuario con paginación del sistema.", security = { @SecurityRequirement(name = "Bearer Authentication") })
    @GetMapping("/user/pageable")
    public ResponseEntity<Page<DeliveryResponse>> getDeliveriesByUserIdPageable(
        @RequestHeader("X-Authenticated-User-Id") String userIdHeader,
        Pageable pageable
    ) {
        Long userId = SecurityUtil.parseUserId(userIdHeader);
        Page<DeliveryResponse> deliveries = deliveryService.findAllByDriverIdPageable(userId, pageable);
        return ResponseEntity.ok(deliveries);
    }

    // Actualizar estado de entrega
    @Operation(summary = "Actualizar estado de entrega", description = "Actualizar el estado de una entrega en el sistema.", security = { @SecurityRequirement(name = "Bearer Authentication") })
    @PostMapping("/{id}/status")
    public ResponseEntity<DeliveryResponse> updateDeliveryStatus(
        @PathVariable Long id,
        @Valid @RequestBody DeliveryStatusRequest deliveryStatusRequest,
        @RequestHeader("X-Authenticated-User-Role") String roleHeader
    ) {
        SecurityUtil.validateRole(roleHeader, Role.DRIVER, Role.ADMIN);
        return ResponseEntity.ok(deliveryService.updateDeliveryStatus(id, deliveryStatusRequest));
    }

    // Verificar si un usuario tiene entregas
    @Hidden
    @GetMapping("/user/{userId}/exists")
    public Boolean driverHasDeliveries(@PathVariable Long userId) {
        return deliveryService.driverHasDeliveries(userId);
    }

    // Verificar si un pedido tiene entregas
    @Hidden
    @GetMapping("/order/{orderId}/exists")
    public Boolean orderHasDeliveries(@PathVariable Long orderId) {
        return deliveryService.orderHasDeliveries(orderId);
    }
}
