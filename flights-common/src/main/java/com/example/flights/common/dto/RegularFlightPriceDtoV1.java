package com.example.flights.common.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegularFlightPriceDtoV1 {
    private final String amount;
    private final String currency;
}
