package com.ds3.team8.deliveries_service.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.ds3.team8.deliveries_service.dtos.DeliveryRequest;
import com.ds3.team8.deliveries_service.dtos.DeliveryResponse;
import com.ds3.team8.deliveries_service.entities.Delivery;

@Component
public class DeliveryMapper {

    public DeliveryResponse toDeliveryResponse(Delivery delivery) {
        if (delivery == null) return null;

        return new DeliveryResponse(
                delivery.getId(),
                delivery.getOrderId(),
                delivery.getDriverId(),
                delivery.getDeliveryStatus(),
                delivery.getPickupAddress(),
                delivery.getDeliveryAddress(),
                delivery.getCreatedAt(),
                delivery.getUpdatedAt()
        );
    }

    public Delivery toDelivery(DeliveryRequest deliveryRequest) {
        if (deliveryRequest == null) return null;

        return new Delivery(
                deliveryRequest.getOrderId(),
                deliveryRequest.getDriverId(),
                deliveryRequest.getPickupAddress(),
                deliveryRequest.getDeliveryAddress()
        );
    }

    public Delivery updateDelivery(Delivery delivery, DeliveryRequest deliveryRequest) {
        if (delivery == null || deliveryRequest == null) return null;

        delivery.setOrderId(deliveryRequest.getOrderId());
        delivery.setDriverId(deliveryRequest.getDriverId());
        delivery.setPickupAddress(deliveryRequest.getPickupAddress());
        delivery.setDeliveryAddress(deliveryRequest.getDeliveryAddress());

        return delivery;
    }

    public List<DeliveryResponse> toDeliveryResponseList(List<Delivery> deliveries) {
        if (deliveries == null) return List.of();
        if (deliveries.isEmpty()) return List.of();
        
        return deliveries.stream()
                .map(this::toDeliveryResponse)
                .collect(Collectors.toList());
    }
}
