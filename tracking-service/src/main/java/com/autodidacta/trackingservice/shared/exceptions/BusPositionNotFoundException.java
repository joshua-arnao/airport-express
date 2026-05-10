package com.autodidacta.trackingservice.shared.exceptions;

public class BusPositionNotFoundException extends RuntimeException {
    public BusPositionNotFoundException(String message) {
        super(message);
    }
}
