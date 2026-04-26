package com.autodidacta.scheduleservice.shared.exceptions;

public class InsufficientSeatsException extends RuntimeException {
    public InsufficientSeatsException(String message) {
        super(message);
    }
}
