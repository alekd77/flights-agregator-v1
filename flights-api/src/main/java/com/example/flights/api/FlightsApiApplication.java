package com.example.flights.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example")
public class FlightsApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(com.example.flights.api.FlightsApiApplication.class, args);
    }
}
