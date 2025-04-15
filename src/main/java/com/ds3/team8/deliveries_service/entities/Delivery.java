package com.ds3.team8.deliveries_service.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "deliveries")
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "dealer_id", nullable = false)
    private Long dealerId;

    @Column(nullable = false)
    private String status;

    @Column(name = "route_details")
    private String routeDetails;
}
