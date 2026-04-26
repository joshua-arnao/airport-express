package com.autodidacta.bookingservice.shared.exceptions;

public class TripNotAvailableException extends RuntimeException {
    public TripNotAvailableException(String message) {
        super(message);
    }
}
