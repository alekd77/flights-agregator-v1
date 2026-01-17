package com.example.flights.common.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class FlightSearchRequestDtoV1 {
    private final String deptCode;
    private final String destCode;
    private final LocalDate deptDate;
}
