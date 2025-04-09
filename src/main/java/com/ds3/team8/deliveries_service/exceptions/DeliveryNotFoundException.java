package com.ds3.team8.deliveries_service.exceptions;

public class DeliveryNotFoundException extends RuntimeException {
    public DeliveryNotFoundException(Long id) {
        super("La entrega con ID " + id + " no fue encontrada.");
    }
    
}
