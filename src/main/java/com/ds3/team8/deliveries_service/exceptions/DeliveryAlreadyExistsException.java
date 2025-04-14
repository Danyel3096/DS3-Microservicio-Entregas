package com.ds3.team8.deliveries_service.exceptions;

public class DeliveryAlreadyExistsException extends RuntimeException {
    public DeliveryAlreadyExistsException(Long id) {
        super("La entrega con identificador '" + id + "' ya existe.");
    }
}
