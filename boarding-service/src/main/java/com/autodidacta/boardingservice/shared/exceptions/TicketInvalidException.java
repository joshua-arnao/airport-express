package com.autodidacta.boardingservice.shared.exceptions;

public class TicketInvalidException extends RuntimeException {
    public TicketInvalidException(String message) {
        super(message);
    }
}
