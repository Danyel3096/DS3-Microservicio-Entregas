package com.ds3.team8.deliveries_service.services;

import com.ds3.team8.deliveries_service.dtos.DeliveryRequest;
import com.ds3.team8.deliveries_service.dtos.DeliveryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IDeliveryService {
    DeliveryResponse save(DeliveryRequest request);
    void delete(Long id);
    DeliveryResponse update(Long id, DeliveryRequest request);
    DeliveryResponse findById(Long id);
    List<DeliveryResponse> findAll();
    Page<DeliveryResponse> findAll(Pageable pageable);
}
