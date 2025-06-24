package com.ds3.team8.deliveries_service.dtos;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryRequest {
    @NotNull(message = "El campo 'orderId' es obligatorio")
    private Long orderId;

    @NotNull(message = "El campo 'driverId' es obligatorio")
    private Long driverId;

    @NotBlank(message = "El campo 'pickupAddress' es obligatorio")
    private String pickupAddress;

    @NotBlank(message = "El campo 'deliveryAddress' es obligatorio")
    private String deliveryAddress;
}