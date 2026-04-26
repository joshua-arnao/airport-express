package com.autodidacta.scheduleservice.shared.exceptions;

public class TripAlreadyExistsException extends RuntimeException {
    public TripAlreadyExistsException(String message) {
        super(message);
    }
}
