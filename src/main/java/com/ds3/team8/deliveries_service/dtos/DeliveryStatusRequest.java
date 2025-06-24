package com.ds3.team8.deliveries_service.dtos;

import com.ds3.team8.deliveries_service.eums.DeliveryStatus;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryStatusRequest {
    @NotNull(message = "El campo 'deliveryStatus' es obligatorio")
    private DeliveryStatus deliveryStatus;
}
