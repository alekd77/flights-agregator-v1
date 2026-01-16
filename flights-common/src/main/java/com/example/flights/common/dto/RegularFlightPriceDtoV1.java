package com.example.flights.common.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class RegularFlightPriceDtoV1 {
    private final BigDecimal amount;
    private final String currency;

    @JsonCreator
    public RegularFlightPriceDtoV1(
            BigDecimal amount,
            String currency) {
        this.amount = amount;
        this.currency = currency;
    }
}
