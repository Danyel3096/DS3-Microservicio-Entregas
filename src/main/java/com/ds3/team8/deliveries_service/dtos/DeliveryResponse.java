package com.ds3.team8.deliveries_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import com.ds3.team8.deliveries_service.eums.DeliveryStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryResponse {
    private Long id;
    private Long orderId;
    private Long driverId;
    private DeliveryStatus deliveryStatus;
    private String pickupAddress;
    private String deliveryAddress;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
