package com.ds3.team8.deliveries_service.repositories;

import com.ds3.team8.deliveries_service.entities.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IDeliveryRepository extends JpaRepository<Delivery, Long> {
    Optional<Delivery> findByIdAndIsActiveTrue(Long id); // Obtener entrega por ID y activo
    List<Delivery> findAllByIsActiveTrue(); // Obtener todas las entregas activas
    Page<Delivery> findAllByIsActiveTrue(Pageable pageable); // Obtener todas las entregas activas con paginación
    List<Delivery> findAllByDriverIdAndIsActiveTrue(Long driverId); // Obtener entregas por ID de usuario y activo
    Page<Delivery> findAllByDriverIdAndIsActiveTrue(Long driverId, Pageable pageable); // Obtener entregas por ID de usuario y activo con paginación
    Boolean existsByDriverIdAndIsActiveTrue(Long driverId); // Verificar si un usuario tiene entregas activas
    Boolean existsByOrderIdAndIsActiveTrue(Long orderId); // Verificar si un pedido tiene entregas activas
}
