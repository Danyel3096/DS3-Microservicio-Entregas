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
    private Integer id;

    @Column(name = "order_id", nullable = false)
    private Integer orderId;

    @Column(name = "dealer_id", nullable = false)
    private Integer dealerId;

    @Column(nullable = false)
    private String status;

    @Column(name = "route_details")
    private String routeDetails;
}
