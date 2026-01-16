package com.example.flights.common.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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
            @JsonProperty("amount") BigDecimal amount,
            @JsonProperty("currency") String currency) {
        this.amount = amount;
        this.currency = currency;
    }
}
