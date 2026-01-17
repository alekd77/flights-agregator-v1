package com.example.flights.common.config;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class RegularFlightProviderClientConfig {
    private final RegularFlightProviderClientConfigProperties properties;

    @Bean(name = "regularFlightWebClient")
    public WebClient regularFlightProviderClient() {
        return WebClient.builder()
                .baseUrl(properties.getBaseUrl())
                .build();
    }
}
