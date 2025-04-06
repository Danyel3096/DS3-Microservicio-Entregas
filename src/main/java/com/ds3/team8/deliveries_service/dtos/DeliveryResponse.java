package com.ds3.team8.deliveries_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryResponse {
    private Long id;
    private String status;
    private String routeDetails;
    private Integer dealerId;
    private Integer orderId;

}
