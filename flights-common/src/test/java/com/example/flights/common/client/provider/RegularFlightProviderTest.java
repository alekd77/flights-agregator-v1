package com.example.flights.common.client.provider;

import com.example.flights.common.client.provider.dto.RegularFlightDto;
import com.example.flights.common.client.provider.dto.RegularFlightPriceDto;
import com.example.flights.common.client.provider.dto.RegularFlightProviderResponseDto;
import com.example.flights.common.config.RegularFlightProviderClientConfigProperties;
import com.example.flights.common.dto.FlightDtoV1;
import com.example.flights.common.dto.FlightSearchRequestDtoV1;
import com.example.flights.common.dto.FlightType;
import com.example.flights.common.exception.FlightClientException;
import com.example.flights.common.exception.FlightProviderUnavailableException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegularFlightProviderTest {

    private MockWebServer mockWebServer;
    private RegularFlightProvider regularFlightProvider;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        String baseUrl = mockWebServer.url("/").toString();
        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();

        RegularFlightProviderClientConfigProperties properties = new RegularFlightProviderClientConfigProperties();
        properties.setName("REGULAR");
        properties.setBaseUrl(baseUrl);
        properties.setFlightDetailsEndpointPath("/api/regular-flights");
        properties.setTimeoutMs(500L);
        properties.setMaxResult(10);

        regularFlightProvider = new RegularFlightProvider(
                webClient,
                properties
        );

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void fetchFlightDetails_shouldReturnMappedFlights_whenProviderReturnsValidResponse() throws Exception {
        // Given
        FlightSearchRequestDtoV1 request = FlightSearchRequestDtoV1.builder()
                .deptCode("JFK")
                .destCode("LAX")
                .deptDate(LocalDate.of(2026, 2, 15))
                .build();

        RegularFlightDto flight1 = RegularFlightDto.builder()
                .id("RF001")
                .deptCode("JFK")
                .destCode("LAX")
                .deptDateTime(LocalDateTime.of(2026, 2, 15, 10, 30))
                .price(RegularFlightPriceDto.builder()
                        .amount(new BigDecimal("350.00"))
                        .currency("USD")
                        .build())
                .duration(Duration.ofHours(6))
                .build();

        RegularFlightDto flight2 = RegularFlightDto.builder()
                .id("RF002")
                .deptCode("JFK")
                .destCode("LAX")
                .deptDateTime(LocalDateTime.of(2026, 2, 15, 14, 45))
                .price(RegularFlightPriceDto.builder()
                        .amount(new BigDecimal("420.50"))
                        .currency("USD")
                        .build())
                .duration(Duration.ofHours(5).plusMinutes(45))
                .build();

        RegularFlightProviderResponseDto providerResponse = RegularFlightProviderResponseDto.builder()
                .data(List.of(flight1, flight2))
                .build();

        mockWebServer.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(providerResponse))
                .addHeader("Content-Type", "application/json"));

        // When
        List<FlightDtoV1> result = regularFlightProvider.fetchFlightDetails(request);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());

        FlightDtoV1 resultFlight1 = result.get(0);
        assertEquals("RF001", resultFlight1.getId());
        assertEquals("JFK", resultFlight1.getDeptCode());
        assertEquals("LAX", resultFlight1.getDestCode());
        assertEquals(LocalDateTime.of(2026, 2, 15, 10, 30), resultFlight1.getDeptDateTime());
        assertEquals(new BigDecimal("350.00"), resultFlight1.getPrice());
        assertEquals("USD", resultFlight1.getCurrency());
        assertEquals(FlightType.REGULAR, resultFlight1.getType());
        assertEquals(Duration.ofHours(6), resultFlight1.getDuration());

        FlightDtoV1 resultFlight2 = result.get(1);
        assertEquals("RF002", resultFlight2.getId());
        assertEquals(new BigDecimal("420.50"), resultFlight2.getPrice());
        assertEquals(FlightType.REGULAR, resultFlight2.getType());

        // Verify the request
        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertEquals("GET", recordedRequest.getMethod());
        assertNotNull(recordedRequest.getPath());
        assertTrue(recordedRequest.getPath().contains("/api/regular-flights"));
        assertTrue(recordedRequest.getPath().contains("departureCode=JFK"));
        assertTrue(recordedRequest.getPath().contains("destinationCode=LAX"));
        assertTrue(recordedRequest.getPath().contains("date=2026-02-15"));
        assertTrue(recordedRequest.getPath().contains("maxResult=10"));
    }

    @Test
    void fetchFlightDetails_shouldReturnEmptyList_whenProviderReturnsNullData() throws Exception {
        // Given
        FlightSearchRequestDtoV1 request = FlightSearchRequestDtoV1.builder()
                .deptCode("JFK")
                .destCode("LAX")
                .deptDate(LocalDate.of(2026, 2, 15))
                .build();

        RegularFlightProviderResponseDto providerResponse = RegularFlightProviderResponseDto.builder()
                .data(null)
                .build();

        mockWebServer.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(providerResponse))
                .addHeader("Content-Type", "application/json"));

        // When
        List<FlightDtoV1> result = regularFlightProvider.fetchFlightDetails(request);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void fetchFlightDetails_shouldReturnEmptyList_whenProviderReturnsEmptyData() throws Exception {
        // Given
        FlightSearchRequestDtoV1 request = FlightSearchRequestDtoV1.builder()
                .deptCode("JFK")
                .destCode("LAX")
                .deptDate(LocalDate.of(2026, 2, 15))
                .build();

        RegularFlightProviderResponseDto providerResponse = RegularFlightProviderResponseDto.builder()
                .data(Collections.emptyList())
                .build();

        mockWebServer.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(providerResponse))
                .addHeader("Content-Type", "application/json"));

        // When
        List<FlightDtoV1> result = regularFlightProvider.fetchFlightDetails(request);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void fetchFlightDetails_shouldThrowFlightProviderUnavailableException_whenProviderReturns5xxError() {
        // Given
        FlightSearchRequestDtoV1 request = FlightSearchRequestDtoV1.builder()
                .deptCode("JFK")
                .destCode("LAX")
                .deptDate(LocalDate.of(2026, 2, 15))
                .build();

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(503)
                .setBody("{\"error\":\"Service Unavailable\"}"));

        // When & Then
        assertThrows(FlightProviderUnavailableException.class, () ->
                regularFlightProvider.fetchFlightDetails(request));
    }

    @Test
    void fetchFlightDetails_shouldThrowFlightClientException_whenProviderReturns4xxError() {
        // Given
        FlightSearchRequestDtoV1 request = FlightSearchRequestDtoV1.builder()
                .deptCode("JFK")
                .destCode("LAX")
                .deptDate(LocalDate.of(2026, 2, 15))
                .build();

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(400)
                .setBody("{\"error\":\"Bad Request\"}"));

        // When & Then
        assertThrows(FlightClientException.class, () ->
                regularFlightProvider.fetchFlightDetails(request));
    }

    @Test
    void fetchFlightDetails_shouldThrowFlightProviderUnavailableException_whenJsonParsingFails() {
        // Given
        FlightSearchRequestDtoV1 request = FlightSearchRequestDtoV1.builder()
                .deptCode("JFK")
                .destCode("LAX")
                .deptDate(LocalDate.of(2026, 2, 15))
                .build();

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("invalid json {{{")
                .addHeader("Content-Type", "application/json"));

        // When & Then
        assertThrows(FlightProviderUnavailableException.class, () ->
                regularFlightProvider.fetchFlightDetails(request));
    }

    @Test
    void fetchFlightDetails_shouldThrowFlightProviderUnavailableException_whenRequestTimesOut() {
        // Given
        FlightSearchRequestDtoV1 request = FlightSearchRequestDtoV1.builder()
                .deptCode("JFK")
                .destCode("LAX")
                .deptDate(LocalDate.of(2026, 2, 15))
                .build();

        // Simulate timeout by delaying the response longer than the configured timeout (500ms)
        mockWebServer.enqueue(new MockResponse()
                .setBody("{\"data\":[]}")
                .addHeader("Content-Type", "application/json")
                .setBodyDelay(1000, java.util.concurrent.TimeUnit.MILLISECONDS));

        // When & Then
        FlightProviderUnavailableException exception = assertThrows(
                FlightProviderUnavailableException.class,
                () -> regularFlightProvider.fetchFlightDetails(request)
        );

        // Verify exception message contains timeout information
        assertTrue(exception.getMessage().contains("REGULAR"));
        assertTrue(exception.getMessage().toLowerCase().contains("timeout") ||
                   exception.getMessage().toLowerCase().contains("unexpected"));
    }
}
