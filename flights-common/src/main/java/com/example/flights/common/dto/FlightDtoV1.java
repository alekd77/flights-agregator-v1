package com.example.flights.common.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

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
    private final FlightType type;

    public FlightDtoV1(String id, String deptCode, String destCode,
                       LocalDateTime deptDateTime, BigDecimal price,
                       String currency, Integer numOfTransfers,
                       Duration duration, FlightType type) {
        this.id = Objects.requireNonNull(id, "id cannot be null");
        this.deptCode = Objects.requireNonNull(deptCode, "deptCode cannot be null");
        this.destCode = Objects.requireNonNull(destCode, "destCode cannot be null");
        this.deptDateTime = Objects.requireNonNull(deptDateTime, "deptDateTime cannot be null");
        this.price = Objects.requireNonNull(price, "price cannot be null");
        this.currency = Objects.requireNonNull(currency, "currency cannot be null");
        this.type = Objects.requireNonNull(type, "type cannot be null");
        this.numOfTransfers = numOfTransfers;
        this.duration = duration;
    }

    public String getSignature() {
        return String.format("%s-%s-%s", deptCode, destCode, deptDateTime.truncatedTo(ChronoUnit.MINUTES));
    }
}
