package com.example.flights.common.client.provider;

import com.example.flights.common.dto.FlightDtoV1;
import com.example.flights.common.dto.FlightSearchRequestDtoV1;

import java.util.List;

public interface FlightProvider {
    List<FlightDtoV1> fetchFlightDetails(FlightSearchRequestDtoV1 request);
}
