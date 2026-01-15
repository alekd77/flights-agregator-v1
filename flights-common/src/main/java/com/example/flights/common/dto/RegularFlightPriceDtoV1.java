package com.example.flights.common.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class RegularFlightPriceDtoV1 {
    private final BigDecimal amount;
    private final String currency;
}
