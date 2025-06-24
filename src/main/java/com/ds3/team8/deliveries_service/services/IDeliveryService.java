package com.ds3.team8.deliveries_service.services;

import com.ds3.team8.deliveries_service.dtos.DeliveryRequest;
import com.ds3.team8.deliveries_service.dtos.DeliveryResponse;
import com.ds3.team8.deliveries_service.dtos.DeliveryStatusRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IDeliveryService {
    DeliveryResponse save(DeliveryRequest deliveryRequest); // Guardar entrega
    void delete(Long id); // Eliminar entrega
    DeliveryResponse update(Long id, DeliveryRequest deliveryRequest); // Actualizar entrega
    DeliveryResponse findById(Long id); // Buscar entrega por ID
    List<DeliveryResponse> findAll(); // Buscar todas las entregas
    Page<DeliveryResponse> findAllPageable(Pageable pageable); // Buscar todas las entregas con paginación
    List<DeliveryResponse> findAllByDriverId(Long driverId); // Buscar entregas por ID de usuario
    Page<DeliveryResponse> findAllByDriverIdPageable(Long driverId, Pageable pageable); // Buscar entregas por ID de usuario con paginación
    DeliveryResponse updateDeliveryStatus(Long id, DeliveryStatusRequest deliveryStatusRequest); // Actualizar estado de entrega
    Boolean driverHasDeliveries(Long driverId); // Verificar si un usuario tiene entregas activas
    Boolean orderHasDeliveries(Long orderId); // Verificar si un pedido tiene entregas activas
}
