package com.example.flights.common.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@Builder
public class FlightDtoV1 {
    private final String id;
    private final String deptCode;
    private final String destCode;
    private final LocalDateTime deptDateTime;
    private final BigDecimal price;
    private final String currency;
    private final Integer numOfTransfers;
    private final Duration duration;
    private final String source;

    public String getSignature() {
        return String.format("%s-%s-%s", deptCode, destCode, deptDateTime.truncatedTo(ChronoUnit.MINUTES));
    }
}
