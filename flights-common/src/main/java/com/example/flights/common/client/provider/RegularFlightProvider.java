package com.example.flights.common.client.provider;

import com.example.flights.common.client.FlightWebClient;
import com.example.flights.common.client.provider.dto.RegularFlightProviderResponseDto;
import com.example.flights.common.config.RegularFlightProviderClientConfigProperties;
import com.example.flights.common.dto.FlightDtoV1;
import com.example.flights.common.dto.FlightSearchRequestDtoV1;
import com.example.flights.common.dto.FlightType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class RegularFlightProvider extends FlightWebClient implements FlightProvider {
    private final RegularFlightProviderClientConfigProperties properties;

    public RegularFlightProvider(
            @Qualifier("regularFlightWebClient") WebClient webClient,
            RegularFlightProviderClientConfigProperties properties) {
        super(webClient);
        this.properties = properties;
    }

    @Override
    public List<FlightDtoV1> fetchFlightDetails(FlightSearchRequestDtoV1 request) {
        return fetchFlightDetailsFromProvider(
                buildUriWithQueryParams(request),
                RegularFlightProviderResponseDto.class
        );
    }

    @Override
    protected String getProviderName() {
        return properties.getName();
    }

    @Override
    protected Duration getProviderTimeout() {
        return Duration.ofMillis(properties.getTimeoutMs());
    }

    @Override
    protected <T> List<FlightDtoV1> mapToDomain(T response) {
        RegularFlightProviderResponseDto responseDto = (RegularFlightProviderResponseDto) response;

        if (responseDto == null || responseDto.getData() == null) {
            return Collections.emptyList();
        }

        return responseDto.getData().stream()
                .map(dto -> FlightDtoV1.builder()
                        .id(dto.getId())
                        .deptCode(dto.getDeptCode())
                        .destCode(dto.getDestCode())
                        .deptDateTime(dto.getDeptDateTime())
                        .price(dto.getPrice().getAmount())
                        .currency(dto.getPrice().getCurrency())
                        .type(FlightType.REGULAR)
                        .duration(dto.getDuration())
                        .build())
                .toList();
    }

    private String buildUriWithQueryParams(FlightSearchRequestDtoV1 request) {
        return UriComponentsBuilder
                .fromPath(properties.getFlightDetailsEndpointPath())
                .queryParam("departureCode", request.getDeptCode())
                .queryParam("destinationCode", request.getDestCode())
                .queryParam("date", request.getDeptDate().toString())
                .queryParam("maxResult", properties.getMaxResult())
                .build()
                .toUriString();
    }
}
