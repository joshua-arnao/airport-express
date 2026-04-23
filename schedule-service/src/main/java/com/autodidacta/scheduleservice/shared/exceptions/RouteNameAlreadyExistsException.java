package com.autodidacta.scheduleservice.shared.exceptions;

public class RouteNameAlreadyExistsException extends RuntimeException {
    public RouteNameAlreadyExistsException(String message) {
        super(message);
    }
}
