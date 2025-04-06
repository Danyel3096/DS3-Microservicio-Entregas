package com.ds3.team8.deliveries_service.dtos;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryRequest {
    @NotEmpty(message = "No puede estar vacio")
    @Size(min = 2, max = 20, message = "El tamaño tiene que estar entre 2 y 20")
    private String status;

    @Size(min = 2, max = 20, message = "El tamaño tiene que estar entre 2 y 20")
    @NotEmpty(message = "No puede estar vacio")
    private String routeDetails;

    @NotNull(message = "El stock no puede ser nulo")
    @Min(value = 0, message = "El dealerId no puede ser negativo")
    private Integer dealerId;

    @NotNull(message = "El stock no puede ser nulo")
    @Min(value = 0, message = "El orderId no puede ser negativo")
    private Integer orderId;
}