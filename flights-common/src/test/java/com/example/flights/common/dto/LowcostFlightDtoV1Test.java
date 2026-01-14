package com.example.flights.common.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class LowcostFlightDtoV1Test {

    @Test
    void testLowcostFlightDtoV1Builder() {
        // When
        LowcostFlightDtoV1 flight = LowcostFlightDtoV1.builder()
                .id("LC456")
                .deptCode("BOS")
                .destCode("MIA")
                .deptDateTime(LocalDateTime.of(2026, 2, 20, 14, 15))
                .numOfTransfers(2)
                .price(new BigDecimal("89.99"))
                .currency("USD")
                .build();

        // Then
        assertNotNull(flight);
        assertEquals("LC456", flight.getId());
        assertEquals("BOS", flight.getDeptCode());
        assertEquals("MIA", flight.getDestCode());
        assertEquals(LocalDateTime.of(2026, 2, 20, 14, 15), flight.getDeptDateTime());
        assertEquals(2, flight.getNumOfTransfers());
        assertEquals(new BigDecimal("89.99"), flight.getPrice());
        assertEquals("USD", flight.getCurrency());
    }
}

