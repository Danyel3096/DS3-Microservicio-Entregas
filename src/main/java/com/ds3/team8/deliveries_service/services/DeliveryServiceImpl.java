package com.ds3.team8.deliveries_service.services;


import com.ds3.team8.deliveries_service.dtos.DeliveryRequest;
import com.ds3.team8.deliveries_service.dtos.DeliveryResponse;
import com.ds3.team8.deliveries_service.entities.Delivery;
import com.ds3.team8.deliveries_service.exceptions.DeliveryNotFoundException;
import com.ds3.team8.deliveries_service.repositories.IDeliveryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DeliveryServiceImpl implements IDeliveryService {
    private final IDeliveryRepository deliveryRepository;

    public DeliveryServiceImpl(IDeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Transactional
    @Override
    public DeliveryResponse save(DeliveryRequest request) {
        Delivery delivery = new Delivery();
        delivery.setStatus(request.getStatus());
        delivery.setRouteDetails(request.getRouteDetails());
        delivery.setDealerId(request.getDealerId());
        delivery.setOrderId(request.getOrderId());
        delivery.setIsActive(true);

        Delivery saved = deliveryRepository.save(delivery);
        return convertToResponse(saved);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new DeliveryNotFoundException(id));
        delivery.setIsActive(false);
        deliveryRepository.save(delivery);
    }

    @Transactional
    @Override
    public DeliveryResponse update(Long id, DeliveryRequest request) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new DeliveryNotFoundException(id));

        delivery.setStatus(request.getStatus());
        delivery.setRouteDetails(request.getRouteDetails());
        delivery.setDealerId(request.getDealerId());
        delivery.setOrderId(request.getOrderId());

        Delivery updated = deliveryRepository.save(delivery);
        return convertToResponse(updated);
    }

    @Transactional(readOnly = true)
    @Override
    public DeliveryResponse findById(Long id) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new DeliveryNotFoundException(id));
        return convertToResponse(delivery);
    }

    @Transactional(readOnly = true)
    @Override
    public List<DeliveryResponse> findAll() {
        return deliveryRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<DeliveryResponse> findAll(Pageable pageable) {
        return deliveryRepository.findAll(pageable)
                .map(this::convertToResponse);
    }

    private DeliveryResponse convertToResponse(Delivery delivery) {
        return new DeliveryResponse(
                delivery.getId(),
                delivery.getStatus(),
                delivery.getRouteDetails(),
                delivery.getDealerId(),
                delivery.getOrderId(),
                delivery.getIsActive()
        );
    }
}
