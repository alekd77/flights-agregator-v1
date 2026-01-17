package com.example.flights.common.exception;

public class FlightClientException extends RuntimeException {

    public FlightClientException(String message) {
        super(message);
    }

    public FlightClientException(String message, Exception exception) {
        super(message, exception);
    }
}
