 package com.example.flights.common.dto;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RegularFlightDtoV1Test {

    @Test
    void testRegularFlightDtoV1Builder() {
        // When
        RegularFlightDtoV1 flight = RegularFlightDtoV1.builder()
                .id("RF123")
                .deptCode("ATL")
                .destCode("DEN")
                .deptDateTime(LocalDateTime.of(2026, 4, 5, 16, 45))
                .price(RegularFlightPriceDtoV1.builder()
                        .amount("350.00")
                        .currency("USD")
                        .build())
                .duration(Duration.ofHours(12).plusMinutes(45))
                .build();

        // Then
        assertNotNull(flight);
        assertEquals("RF123", flight.getId());
        assertEquals("ATL", flight.getDeptCode());
        assertEquals("DEN", flight.getDestCode());
        assertEquals(LocalDateTime.of(2026, 4, 5, 16, 45), flight.getDeptDateTime());
        assertEquals(Duration.ofHours(12).plusMinutes(45), flight.getDuration());
        assertEquals("350.00", flight.getPrice().getAmount());
        assertEquals("USD", flight.getPrice().getCurrency());
    }
}

