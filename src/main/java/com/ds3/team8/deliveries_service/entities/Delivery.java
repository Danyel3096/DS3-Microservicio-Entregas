package com.ds3.team8.deliveries_service.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.ds3.team8.deliveries_service.eums.DeliveryStatus;

@Data
@Entity
@Table(name = "deliveries")
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "driver_id", nullable = false)
    private Long driverId; // ID del repartidor asignado

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_status", nullable = false)
    private DeliveryStatus deliveryStatus = DeliveryStatus.PENDING_DELIVERY; // Estado de la entrega

    @Column(name = "pickup_address", nullable = false)
    private String pickupAddress; // Dirección de recogida

    @Column(name = "delivery_address", nullable = false)
    private String deliveryAddress; // Dirección de entrega

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @PreUpdate
    public void setLastUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Delivery(Long orderId, Long driverId, String pickupAddress, String deliveryAddress) {
        this.orderId = orderId;
        this.driverId = driverId;
        this.pickupAddress = pickupAddress;
        this.deliveryAddress = deliveryAddress;
    }
}
