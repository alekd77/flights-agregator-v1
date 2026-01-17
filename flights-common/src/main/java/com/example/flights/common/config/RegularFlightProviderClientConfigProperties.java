package com.example.flights.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "provider.regular-flight")
@Data
public class RegularFlightProviderClientConfigProperties {
    private String name;
    private String baseUrl;
    private String flightDetailsEndpointPath;
    private long timeoutMs;
    private int maxResult;
}
