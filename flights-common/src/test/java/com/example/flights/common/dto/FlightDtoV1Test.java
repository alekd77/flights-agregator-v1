package com.example.flights.common.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FlightDtoV1Test {

    @Test
    void flightDtoV1Builder_shouldCreateValidFlightDto_whenAllRequiredFieldsProvided() {
        // When
        FlightDtoV1 flight = FlightDtoV1.builder()
                .id("FL123")
                .deptCode("NYC")
                .destCode("LAX")
                .deptDateTime(LocalDateTime.of(2026, 1, 15, 10, 30))
                .price(new BigDecimal("299.99"))
                .currency("USD")
                .type(FlightType.REGULAR)
                .build();

        // Then
        assertNotNull(flight);
        assertEquals("FL123", flight.getId());
        assertEquals("NYC", flight.getDeptCode());
        assertEquals("LAX", flight.getDestCode());
        assertEquals(LocalDateTime.of(2026, 1, 15, 10, 30), flight.getDeptDateTime());
        assertEquals(new BigDecimal("299.99"), flight.getPrice());
        assertEquals("USD", flight.getCurrency());
        assertEquals(FlightType.REGULAR, flight.getType());
    }

    @Test
    void flightDtoV1Builder_shouldThrowNullPointerException_whenIdMissing() {
        // When & Then
        NullPointerException exception = assertThrows(NullPointerException.class, () ->
                FlightDtoV1.builder()
                        .deptCode("NYC")
                        .destCode("LAX")
                        .deptDateTime(LocalDateTime.of(2026, 1, 15, 10, 30))
                        .price(new BigDecimal("299.99"))
                        .currency("USD")
                        .type(FlightType.REGULAR)
                        .build()
        );
        assertEquals("id cannot be null", exception.getMessage());
    }

    @Test
    void flightDtoV1Builder_shouldThrowNullPointerException_whenDeptCodeMissing() {
        // When & Then
        NullPointerException exception = assertThrows(NullPointerException.class, () ->
                FlightDtoV1.builder()
                        .id("FL123")
                        .destCode("LAX")
                        .deptDateTime(LocalDateTime.of(2026, 1, 15, 10, 30))
                        .price(new BigDecimal("299.99"))
                        .currency("USD")
                        .type(FlightType.REGULAR)
                        .build()
        );
        assertEquals("deptCode cannot be null", exception.getMessage());
    }

    @Test
    void flightDtoV1Builder_shouldThrowNullPointerException_whenDestCodeMissing() {
        // When & Then
        NullPointerException exception = assertThrows(NullPointerException.class, () ->
                FlightDtoV1.builder()
                        .id("FL123")
                        .deptCode("NYC")
                        .deptDateTime(LocalDateTime.of(2026, 1, 15, 10, 30))
                        .price(new BigDecimal("299.99"))
                        .currency("USD")
                        .type(FlightType.REGULAR)
                        .build()
        );
        assertEquals("destCode cannot be null", exception.getMessage());
    }

    @Test
    void flightDtoV1Builder_shouldThrowNullPointerException_whenDeptDateTimeMissing() {
        // When & Then
        NullPointerException exception = assertThrows(NullPointerException.class, () ->
                FlightDtoV1.builder()
                        .id("FL123")
                        .deptCode("NYC")
                        .destCode("LAX")
                        .price(new BigDecimal("299.99"))
                        .currency("USD")
                        .type(FlightType.REGULAR)
                        .build()
        );
        assertEquals("deptDateTime cannot be null", exception.getMessage());
    }

    @Test
    void flightDtoV1Builder_shouldThrowNullPointerException_whenPriceMissing() {
        // When & Then
        NullPointerException exception = assertThrows(NullPointerException.class, () ->
                FlightDtoV1.builder()
                        .id("FL123")
                        .deptCode("NYC")
                        .destCode("LAX")
                        .deptDateTime(LocalDateTime.of(2026, 1, 15, 10, 30))
                        .currency("USD")
                        .type(FlightType.REGULAR)
                        .build()
        );
        assertEquals("price cannot be null", exception.getMessage());
    }

    @Test
    void flightDtoV1Builder_shouldThrowNullPointerException_whenCurrencyMissing() {
        // When & Then
        NullPointerException exception = assertThrows(NullPointerException.class, () ->
                FlightDtoV1.builder()
                        .id("FL123")
                        .deptCode("NYC")
                        .destCode("LAX")
                        .deptDateTime(LocalDateTime.of(2026, 1, 15, 10, 30))
                        .price(new BigDecimal("299.99"))
                        .type(FlightType.REGULAR)
                        .build()
        );
        assertEquals("currency cannot be null", exception.getMessage());
    }

    @Test
    void flightDtoV1Builder_shouldThrowNullPointerException_whenTypeMissing() {
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
        assertEquals("type cannot be null", exception.getMessage());
    }

    @Test
    void getSignature_shouldTruncateSeconds_whenCalled() {
        // When
        FlightDtoV1 flight = FlightDtoV1.builder()
                .id("FL123")
                .deptCode("NYC")
                .destCode("LAX")
                .deptDateTime(LocalDateTime.of(2026, 1, 15, 10, 30))
                .price(new BigDecimal("299.99"))
                .currency("USD")
                .type(FlightType.REGULAR)
                .build();

        // Then
        String signature = flight.getSignature();
        assertEquals("NYC-LAX-2026-01-15T10:30", signature);
        assertFalse(signature.contains("59"));
    }
}