package com.ds3.team8.deliveries_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DeliveriesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliveriesServiceApplication.class, args);
	}

}
