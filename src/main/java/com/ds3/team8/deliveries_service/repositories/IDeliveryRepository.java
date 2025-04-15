package com.ds3.team8.deliveries_service.repositories;

import com.ds3.team8.deliveries_service.entities.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDeliveryRepository extends JpaRepository<Delivery, Long> {
}
