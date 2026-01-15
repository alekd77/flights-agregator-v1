package com.example.flights.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class LowcostFlightDtoV1 {
    private final String id;
    private final String deptCode;
    private final String destCode;
    @JsonProperty("time")
    private final LocalDateTime deptDateTime;
    @JsonProperty("numberOfTransfers")
    private final Integer numOfTransfers;
    private final BigDecimal price;
    private final String currency;
}
