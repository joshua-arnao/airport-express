package com.autodidacta.bookingservice.dto;

import com.autodidacta.bookingservice.entity.DocumentType;

import java.util.UUID;

public record PassengerResponse(
        UUID passengerId,
        UUID ticketId,
        String firstName,
        String lastName,
        DocumentType documentType,
        String documentNumber,
        String email,
        Boolean hasBaggage
) {
}
