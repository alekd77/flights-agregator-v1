package com.example.flights.common.client;

import com.example.flights.common.dto.FlightDtoV1;
import com.example.flights.common.exception.FlightClientException;
import com.example.flights.common.exception.FlightProviderUnavailableException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Slf4j
@AllArgsConstructor
public abstract class FlightWebClient {
    private final WebClient webClient;

    protected <T> List<FlightDtoV1> fetchFlightDetailsFromProvider(String uri, Class<T> responseType) {
        log.info("Fetching flights from providerName: {}", getProviderName());

        T response = performBlockingGetRequest(uri, responseType);
        List<FlightDtoV1> flights = mapToDomain(response);

        log.info("Provider: {} returned {} flights", getProviderName(), flights.size());
        return flights;
    }

    private <T> T performBlockingGetRequest(String uri, Class<T> responseType) {
        log.debug("GET request to URI: {}", uri);

        try {
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, this::handle4xxError)
                    .onStatus(HttpStatusCode::is5xxServerError, this::handle5xxError)
                    .bodyToMono(responseType)
                    .timeout(getProviderTimeout())
                    .block();
        } catch (FlightClientException e) {
            log.error("4xx error from provider '{}': {}", getProviderName(), e.getMessage());
            throw e;
        } catch (FlightProviderUnavailableException e) {
            log.error("5xx error from provider '{}': {}", getProviderName(), e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error from provider '{}': {}", getProviderName(), e.getMessage(), e);
            throw new FlightProviderUnavailableException(getProviderName(),
                    "Unexpected error: " + e.getMessage());
        }
    }

    private Mono<? extends Throwable> handle4xxError(ClientResponse response) {
        return response.bodyToMono(String.class)
                .flatMap(body -> {
                    log.error("4xx error from provider '{}': Status {}, Body: {}",
                            getProviderName(), response.statusCode(), body);
                    return Mono.error(new FlightClientException(
                            String.format("Client error %s: %s", response.statusCode(), body)));
                });
    }

    private Mono<? extends Throwable> handle5xxError(ClientResponse response) {
        return response.bodyToMono(String.class)
                .flatMap(body -> {
                    log.error("5xx error from provider '{}': Status {}, Body: {}",
                            getProviderName(), response.statusCode(), body);
                    return Mono.error(new FlightProviderUnavailableException(getProviderName(),
                            String.format("Server error %s: %s", response.statusCode(), body)));
                });
    }

    protected abstract String getProviderName();
    protected abstract Duration getProviderTimeout();
    protected abstract <T> List<FlightDtoV1> mapToDomain(T response);
}
