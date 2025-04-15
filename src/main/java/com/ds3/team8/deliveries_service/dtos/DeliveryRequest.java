package com.ds3.team8.deliveries_service.dtos;

import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryRequest {
    @NotEmpty(message = "No puede estar vacio")
    @Size(min = 2, max = 20, message = "El tamaño tiene que estar entre 2 y 20")
    private String status;

    @NotNull(message = "La fecha de entrega no puede estar vacía")
    @Future(message = "La fecha de entrega debe estar en el futuro")
    private LocalDateTime deliveryDate;

    @NotNull(message = "El stock no puede ser nulo")
    @Min(value = 0, message = "El dealerId no puede ser negativo")
    private Long userId;

    @NotNull(message = "El stock no puede ser nulo")
    @Min(value = 0, message = "El orderId no puede ser negativo")
    private Long orderId;
}