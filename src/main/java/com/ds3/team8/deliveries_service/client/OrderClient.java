package com.ds3.team8.deliveries_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.ds3.team8.deliveries_service.client.dtos.OrderResponse;
import com.ds3.team8.deliveries_service.client.dtos.OrderStatusRequest;
import com.ds3.team8.deliveries_service.config.FeignClientInterceptor;

@FeignClient(name = "orders-service", configuration = FeignClientInterceptor.class)
public interface OrderClient {
    
    @GetMapping("/api/v1/orders/{id}")
    OrderResponse getOrderById(@PathVariable("id") Long id);

    @PostMapping("/api/v1/orders/{id}/status")
    OrderResponse updateOrderStatus(@PathVariable("id") Long id, @RequestBody OrderStatusRequest orderStatusRequest);
}
