package com.ds3.team8.deliveries_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryResponse {
    private Long id;
    private String status;
    private LocalDateTime deliveryDate;
    private Long userId;
    private Long orderId;
    private Boolean isActive;

}
