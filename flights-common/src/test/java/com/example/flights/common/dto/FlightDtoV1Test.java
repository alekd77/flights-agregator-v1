package com.example.flights.common.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
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
        assertEquals("test-source", flight.getSource());
    }

    @Test
    void testFlightDtoV1Builder_MissingId_ThrowsNullPointerException() {
        // When & Then
        NullPointerException exception = assertThrows(NullPointerException.class, () ->
                FlightDtoV1.builder()
                        .deptCode("NYC")
                        .destCode("LAX")
                        .deptDateTime(LocalDateTime.of(2026, 1, 15, 10, 30))
                        .price(new BigDecimal("299.99"))
                        .currency("USD")
                        .source("test-source")
                        .build()
        );
        assertEquals("id cannot be null", exception.getMessage());
    }

    @Test
    void testFlightDtoV1Builder_MissingDeptCode_ThrowsNullPointerException() {
        // When & Then
        NullPointerException exception = assertThrows(NullPointerException.class, () ->
                FlightDtoV1.builder()
                        .id("FL123")
                        .destCode("LAX")
                        .deptDateTime(LocalDateTime.of(2026, 1, 15, 10, 30))
                        .price(new BigDecimal("299.99"))
                        .currency("USD")
                        .source("test-source")
                        .build()
        );
        assertEquals("deptCode cannot be null", exception.getMessage());
    }

    @Test
    void testFlightDtoV1Builder_MissingDestCode_ThrowsNullPointerException() {
        // When & Then
        NullPointerException exception = assertThrows(NullPointerException.class, () ->
                FlightDtoV1.builder()
                        .id("FL123")
                        .deptCode("NYC")
                        .deptDateTime(LocalDateTime.of(2026, 1, 15, 10, 30))
                        .price(new BigDecimal("299.99"))
                        .currency("USD")
                        .source("test-source")
                        .build()
        );
        assertEquals("destCode cannot be null", exception.getMessage());
    }

    @Test
    void testFlightDtoV1Builder_MissingDeptDateTime_ThrowsNullPointerException() {
        // When & Then
        NullPointerException exception = assertThrows(NullPointerException.class, () ->
                FlightDtoV1.builder()
                        .id("FL123")
                        .deptCode("NYC")
                        .destCode("LAX")
                        .price(new BigDecimal("299.99"))
                        .currency("USD")
                        .source("test-source")
                        .build()
        );
        assertEquals("deptDateTime cannot be null", exception.getMessage());
    }

    @Test
    void testFlightDtoV1Builder_MissingPrice_ThrowsNullPointerException() {
        // When & Then
        NullPointerException exception = assertThrows(NullPointerException.class, () ->
                FlightDtoV1.builder()
                        .id("FL123")
                        .deptCode("NYC")
                        .destCode("LAX")
                        .deptDateTime(LocalDateTime.of(2026, 1, 15, 10, 30))
                        .currency("USD")
                        .source("test-source")
                        .build()
        );
        assertEquals("price cannot be null", exception.getMessage());
    }

    @Test
    void testFlightDtoV1Builder_MissingCurrency_ThrowsNullPointerException() {
        // When & Then
        NullPointerException exception = assertThrows(NullPointerException.class, () ->
                FlightDtoV1.builder()
                        .id("FL123")
                        .deptCode("NYC")
                        .destCode("LAX")
                        .deptDateTime(LocalDateTime.of(2026, 1, 15, 10, 30))
                        .price(new BigDecimal("299.99"))
                        .source("test-source")
                        .build()
        );
        assertEquals("currency cannot be null", exception.getMessage());
    }

    @Test
    void testFlightDtoV1Builder_MissingSource_ThrowsNullPointerException() {
        // When & Then
        NullPointerException exception = assertThrows(NullPointerException.class, () ->
                FlightDtoV1.builder()
                        .id("FL123")
                        .deptCode("NYC")
                        .destCode("LAX")
                        .deptDateTime(LocalDateTime.of(2026, 1, 15, 10, 30))
                        .price(new BigDecimal("299.99"))
                        .currency("USD")
                        .build()
        );
        assertEquals("source cannot be null", exception.getMessage());
    }

    @Test
    void testGetSignatureTruncatesSeconds() {
        // When
        FlightDtoV1 flight = FlightDtoV1.builder()
                .id("FL123")
                .deptCode("NYC")
                .destCode("LAX")
                .deptDateTime(LocalDateTime.of(2026, 1, 15, 10, 30))
                .price(new BigDecimal("299.99"))
                .currency("USD")
                .source("test-source")
                .build();

        // Then
        String signature = flight.getSignature();
        assertEquals("NYC-LAX-2026-01-15T10:30", signature);
        assertFalse(signature.contains("59"));
    }
}