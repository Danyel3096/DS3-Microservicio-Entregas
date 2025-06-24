package com.ds3.team8.deliveries_service.services;

import com.ds3.team8.deliveries_service.exceptions.BadRequestException;
import com.ds3.team8.deliveries_service.exceptions.NotFoundException;
import com.ds3.team8.deliveries_service.mappers.DeliveryMapper;
import com.ds3.team8.deliveries_service.client.OrderClient;
import com.ds3.team8.deliveries_service.client.UserClient;
import com.ds3.team8.deliveries_service.client.dtos.OrderResponse;
import com.ds3.team8.deliveries_service.client.dtos.OrderStatusRequest;
import com.ds3.team8.deliveries_service.client.dtos.UserResponse;
import com.ds3.team8.deliveries_service.client.enums.OrderStatus;
import com.ds3.team8.deliveries_service.client.enums.Role;
import com.ds3.team8.deliveries_service.dtos.DeliveryRequest;
import com.ds3.team8.deliveries_service.dtos.DeliveryResponse;
import com.ds3.team8.deliveries_service.dtos.DeliveryStatusRequest;
import com.ds3.team8.deliveries_service.entities.Delivery;
import com.ds3.team8.deliveries_service.eums.DeliveryStatus;
import com.ds3.team8.deliveries_service.repositories.IDeliveryRepository;

import feign.FeignException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DeliveryServiceImpl implements IDeliveryService {

    private final IDeliveryRepository deliveryRepository;
    private final DeliveryMapper deliveryMapper;
    private final OrderClient orderClient;
    private final UserClient userClient;

    private static final Logger logger = LoggerFactory.getLogger(DeliveryServiceImpl.class);


    public DeliveryServiceImpl(IDeliveryRepository deliveryRepository, DeliveryMapper deliveryMapper, OrderClient orderClient, UserClient userClient) {
        this.deliveryRepository = deliveryRepository;
        this.deliveryMapper = deliveryMapper;
        this.orderClient = orderClient;
        this.userClient = userClient;
    }

    @Transactional
    @Override
    public DeliveryResponse save(DeliveryRequest deliveryRequest) {
        // Validar que el pedido existe
        validateOrderAndStatus(deliveryRequest.getOrderId(), OrderStatus.PAID);
        // Validar que el usuario existe
        validateUser(deliveryRequest.getDriverId());

        // Crear nueva entrega
        Delivery delivery = deliveryMapper.toDelivery(deliveryRequest);
        Delivery savedDelivery = deliveryRepository.save(delivery);
        logger.info("Entrega creada con ID: {}", savedDelivery.getId());
        // Mapear a DTO
        return deliveryMapper.toDeliveryResponse(savedDelivery);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Optional<Delivery> deliveryOptional = deliveryRepository.findByIdAndIsActiveTrue(id);
        if (deliveryOptional.isEmpty()) {
            logger.error("Entrega no encontrada con ID: {}", id);
            throw new NotFoundException("Entrega no encontrada");
        }

        // Marcar entrega como inactiva
        Delivery delivery = deliveryOptional.get();
        delivery.setIsActive(false);
        deliveryRepository.save(delivery);
        logger.info("Entrega eliminada con ID: {}", id);
    }

    @Transactional
    @Override
    public DeliveryResponse update(Long id, DeliveryRequest deliveryRequest) {
        Optional<Delivery> deliveryOptional = deliveryRepository.findByIdAndIsActiveTrue(id);
        if (deliveryOptional.isEmpty()) {
            logger.error("Entrega no encontrada con ID: {}", id);
            throw new NotFoundException("Entrega no encontrada");
        }

        // Validar que el pedido existe
        validateOrderAndStatus(deliveryRequest.getOrderId(), OrderStatus.PAID);
        // Validar que el usuario existe
        validateUser(deliveryRequest.getDriverId());

        // Actualizar entrega
        Delivery delivery = deliveryMapper.updateDelivery(deliveryOptional.get(), deliveryRequest);
        Delivery updatedDelivery = deliveryRepository.save(delivery);
        logger.info("Entrega actualizada con ID: {}", updatedDelivery.getId());
        // Mapear a DTO
        return deliveryMapper.toDeliveryResponse(updatedDelivery);
    }

    @Transactional(readOnly = true)
    @Override
    public DeliveryResponse findById(Long id) {
        Optional<Delivery> deliveryOptional = deliveryRepository.findByIdAndIsActiveTrue(id);
        if (deliveryOptional.isEmpty()) {
            logger.error("Entrega no encontrada con ID: {}", id);
            throw new NotFoundException("Entrega no encontrada");
        }
        // Mapear a DTO
        logger.info("Entrega encontrada con ID: {}", id);
        return deliveryMapper.toDeliveryResponse(deliveryOptional.get());
    }

    @Transactional(readOnly = true)
    @Override
    public List<DeliveryResponse> findAll() {
        // Obtener todas las entregas activas
        List<Delivery> deliveries = deliveryRepository.findAllByIsActiveTrue();
        // Mapear a DTO
        if (deliveries.isEmpty()) {
            logger.warn("No se encontraron entregas activas.");
            throw new NotFoundException("No se encontraron entregas activas");
        }
        logger.info("Se encontraron {} entregas activas.", deliveries.size());
        return deliveryMapper.toDeliveryResponseList(deliveries);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<DeliveryResponse> findAllPageable(Pageable pageable) {
        // Obtener todas las entregas activas con paginación
        Page<Delivery> deliveries = deliveryRepository.findAllByIsActiveTrue(pageable);
        // Mapear a DTO
        if (deliveries.isEmpty()) {
            logger.warn("No se encontraron entregas activas.");
            throw new NotFoundException("No se encontraron entregas activas");
        }
        logger.info("Se encontraron {} entregas activas.", deliveries.getTotalElements());
        return deliveries.map(deliveryMapper::toDeliveryResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public List<DeliveryResponse> findAllByDriverId(Long driverId) {
        // Validar que el usuario existe
        validateUser(driverId);
        // Obtener todas las entregas activas por ID de usuario
        List<Delivery> deliveries = deliveryRepository.findAllByDriverIdAndIsActiveTrue(driverId);
        logger.info("Se encontraron {} entregas activas para el conductor con ID: {}", deliveries.size(), driverId);
        // Mapear a DTO
        return deliveryMapper.toDeliveryResponseList(deliveries);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<DeliveryResponse> findAllByDriverIdPageable(Long driverId, Pageable pageable) {
        // Validar que el usuario existe
        validateUser(driverId);
        // Obtener todas las entregas activas por ID de usuario con paginación
        Page<Delivery> deliveries = deliveryRepository.findAllByDriverIdAndIsActiveTrue(driverId, pageable);
        if (deliveries.isEmpty()) {
            logger.warn("No se encontraron entregas activas para el conductor con ID: {}", driverId);
            throw new NotFoundException("No se encontraron entregas activas para el conductor");
        }
        logger.info("Se encontraron {} entregas activas para el conductor con ID: {}", deliveries.getTotalElements(), driverId);
        // Mapear a DTO
        return deliveries.map(deliveryMapper::toDeliveryResponse);
    }

    @Transactional
    @Override
    public DeliveryResponse updateDeliveryStatus(Long id, DeliveryStatusRequest deliveryStatusRequest) {
        Optional<Delivery> deliveryOptional = deliveryRepository.findByIdAndIsActiveTrue(id);
        if (deliveryOptional.isEmpty()) {
            logger.error("Entrega no encontrada con ID: {}", id);
            throw new NotFoundException("Entrega no encontrada");
        }
        // Actualizar estado de entrega
        Delivery delivery = deliveryOptional.get();
        switch (deliveryStatusRequest.getDeliveryStatus()) {
            case IN_TRANSIT:
                if (delivery.getDeliveryStatus() != DeliveryStatus.PENDING_DELIVERY) {
                    throw new BadRequestException("Solo se puede poner en camino una entrega pendiente de entrega.");
                }

                // Actualizar el estado del pedido a "PROCESSING"
                updateOrderStatus(delivery.getOrderId(), OrderStatus.PROCESSING);

                break;
            case DELIVERED:
                if (delivery.getDeliveryStatus() != DeliveryStatus.IN_TRANSIT) {
                    throw new BadRequestException("Solo se puede marcar como entregada una entrega en camino.");
                }

                // Actualizar el estado del pedido a "COMPLETED"
                updateOrderStatus(delivery.getOrderId(), OrderStatus.COMPLETED);
                break;
            case CANCELED:
                if (delivery.getDeliveryStatus() == DeliveryStatus.DELIVERED) {
                    throw new BadRequestException("No se puede cancelar una entrega ya entregada.");
                }

                // Actualizar el estado del pedido a "CANCELED"
                updateOrderStatus(delivery.getOrderId(), OrderStatus.CANCELED);

                break;
            default:
                throw new BadRequestException("Transición de estado no permitida.");
        }
        delivery.setDeliveryStatus(deliveryStatusRequest.getDeliveryStatus());
        Delivery updatedDelivery = deliveryRepository.save(delivery);
        logger.info("Estado de entrega actualizado a {} para la entrega con ID: {}", deliveryStatusRequest.getDeliveryStatus(), id);
        // Mapear a DTO
        return deliveryMapper.toDeliveryResponse(updatedDelivery);
    }

    @Transactional(readOnly = true)
    @Override
    public Boolean driverHasDeliveries(Long driverId) {
        // Validar que el usuario existe
        validateUser(driverId);

        // Verificar si el usuario tiene entregas activas
        logger.info("Verificando si el conductor con ID: {} tiene entregas activas.", driverId);
        return deliveryRepository.existsByDriverIdAndIsActiveTrue(driverId);
    }

    @Transactional(readOnly = true)
    @Override
    public Boolean orderHasDeliveries(Long orderId) {
        // Validar que el pedido existe
        validateOrder(orderId);

        logger.info("Verificando si el pedido con ID: {} tiene entregas activas.", orderId);
        return deliveryRepository.existsByOrderIdAndIsActiveTrue(orderId);
    }

    private void validateUser(Long userId) {
        try {
            UserResponse userResponse = userClient.getUserById(userId);
            logger.info("Usuario con ID {} validado correctamente", userId);

            // Validar que el usuario es un conductor
            if (!userResponse.getRole().equals(Role.DRIVER)) {
                logger.error("El usuario con ID: {} no es un conductor.", userId);
                throw new BadRequestException("El usuario no es un conductor.");
            }

        } catch (FeignException e) {
            logger.error("Error al validar el usuario: {}", e.getMessage(), e);
            throw new RuntimeException("No se pudo validar el usuario", e);
        }
    }

    private void validateOrder(Long orderId) {
        try {
            orderClient.getOrderById(orderId);
            logger.info("Pedido con ID {} validado correctamente", orderId);
        } catch (FeignException e) {
            logger.error("Error al validar el pedido: {}", e.getMessage(), e);
            throw new RuntimeException("No se pudo validar el pedido", e);
        }
    }

    private OrderResponse validateOrderAndStatus(Long orderId, OrderStatus expectedStatus) {
        try {
            OrderResponse order = orderClient.getOrderById(orderId);
            if (!order.getOrderStatus().equals(expectedStatus)) {
                logger.error("El pedido con ID: {} no está en estado '{}'.", orderId, expectedStatus);
                throw new RuntimeException("El pedido debe estar en estado '" + expectedStatus + "'.");
            }
            logger.info("Pedido con ID {} validado correctamente con estado {}", orderId, expectedStatus);
            return order;
        } catch (FeignException e) {
            logger.error("Error al validar el pedido: {}", e.getMessage());
            throw new RuntimeException("No se pudo obtener el pedido. Intente más tarde.");
        }
    }

    private void updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        try {
            orderClient.updateOrderStatus(orderId, new OrderStatusRequest(orderStatus));
            logger.info("Estado del pedido con ID {} actualizado a {}", orderId, orderStatus);
        } catch (FeignException e) {
            logger.error("Error al actualizar el estado del pedido: {}", e.getMessage());
            throw new RuntimeException("No se pudo actualizar el estado del pedido. Intente más tarde.");
        }
    }
}
