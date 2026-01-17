package com.example.flights.common.client.provider.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegularFlightDto {
    private String id;
    private String deptCode;
    private String destCode;
    private LocalDateTime deptDateTime;
    private RegularFlightPriceDto price;
    private Duration duration;
}
