package com.example.flights.common.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegularFlightPriceDtoV1Test {

    @Test
    void testRegularFlightPriceDtoV1Builder() {
        // When
        RegularFlightPriceDtoV1 price = RegularFlightPriceDtoV1.builder()
                .amount("450.00")
                .currency("EUR")
                .build();

        // Then
        assertNotNull(price);
        assertEquals("450.00", price.getAmount());
        assertEquals("EUR", price.getCurrency());
    }
}

