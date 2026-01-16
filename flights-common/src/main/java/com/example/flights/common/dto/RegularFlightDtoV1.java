package com.example.flights.common.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.Duration;

@Data
@Builder
public class RegularFlightDtoV1 {
    private final String id;
    private final String deptCode;
    private final String destCode;
    private final LocalDateTime deptDateTime;
    private final RegularFlightPriceDtoV1 price;
    private final Duration duration;

    @JsonCreator
    public RegularFlightDtoV1(
            @JsonProperty("id") String id,
            @JsonProperty("deptCode") String deptCode,
            @JsonProperty("destCode") String destCode,
            @JsonProperty("deptDateTime") LocalDateTime deptDateTime,
            @JsonProperty("price") RegularFlightPriceDtoV1 price,
            @JsonProperty("duration") Duration duration) {
        this.id = id;
        this.deptCode = deptCode;
        this.destCode = destCode;
        this.deptDateTime = deptDateTime;
        this.price = price;
        this.duration = duration;
    }

}
