package com.example.flights.common.exception;

public class FlightProviderUnavailableException extends RuntimeException {

    public FlightProviderUnavailableException(String providerName, String message) {
        super(String.format("Provider %s unavailable: %s", providerName, message));
    }
}
