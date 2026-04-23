package com.autodidacta.scheduleservice.shared.exceptions;

public class RouteNotFoundException extends RuntimeException {
    public RouteNotFoundException(String message) {
        super(message);
    }
}
