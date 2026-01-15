package com.example.flights.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.Duration;

@Data
@Builder
public class RegularFlightDtoV1 {
    private final String id;
    @JsonProperty("departureCode")
    private final String deptCode;
    @JsonProperty("destinationCode")
    private final String destCode;
    @JsonProperty("dateTime")
    private final LocalDateTime deptDateTime;
    private final RegularFlightPriceDtoV1 price;
    private final Duration duration;
}
