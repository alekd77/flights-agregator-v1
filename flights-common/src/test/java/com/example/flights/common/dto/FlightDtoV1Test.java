package com.example.flights.common.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FlightDtoV1Test {

    @Test
    void testFlightDtoV1Builder() {
        // When
        FlightDtoV1 flight = FlightDtoV1.builder()
                .id("FL123")
                .deptCode("NYC")
                .destCode("LAX")
                .deptDateTime(LocalDateTime.of(2026, 1, 15, 10, 30))
                .price(new BigDecimal("299.99"))
                .currency("USD")
                .numOfTransfers(1)
                .duration(Duration.ofHours(5))
                .source("test-source")
                .build();

        // Then
        assertNotNull(flight);
        assertEquals("FL123", flight.getId());
        assertEquals("NYC", flight.getDeptCode());
        assertEquals("LAX", flight.getDestCode());
        assertEquals(LocalDateTime.of(2026, 1, 15, 10, 30), flight.getDeptDateTime());
        assertEquals(new BigDecimal("299.99"), flight.getPrice());
        assertEquals("USD", flight.getCurrency());
        assertEquals(1, flight.getNumOfTransfers());
        assertEquals(Duration.ofHours(5), flight.getDuration());
        assertEquals("test-source", flight.getSource());
    }

    @Test
    void testGetSignature() {
        // When
        FlightDtoV1 flight = FlightDtoV1.builder()
                .deptCode("NYC")
                .destCode("LAX")
                .deptDateTime(LocalDateTime.of(2026, 1, 15, 10, 30, 45))
                .build();

        // Then
        String signature = flight.getSignature();
        assertEquals("NYC-LAX-2026-01-15T10:30", signature);
    }

    @Test
    void testGetSignatureTruncatesSeconds() {
        // When
        FlightDtoV1 flight = FlightDtoV1.builder()
                .deptCode("JFK")
                .destCode("SFO")
                .deptDateTime(LocalDateTime.of(2026, 1, 15, 10, 30, 59, 999999999))
                .build();

        // Then
        String signature = flight.getSignature();
        assertEquals("JFK-SFO-2026-01-15T10:30", signature);
        assertFalse(signature.contains("59"));
    }
}