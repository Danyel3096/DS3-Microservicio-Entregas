package com.ds3.team8.deliveries_service.exceptions;

public class DeliveryDeletionException extends RuntimeException {
    public DeliveryDeletionException(Long id) {
        super("La entrega con ID " + id + " no puede eliminarse porque tiene órdenes asociadas.");
    }    
}
