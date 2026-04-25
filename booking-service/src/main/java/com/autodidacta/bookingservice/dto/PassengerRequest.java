package com.autodidacta.bookingservice.dto;

import com.autodidacta.bookingservice.entity.DocumentType;

public record PassengerRequest(
    String firstName,
    String lastName,
    DocumentType documentType,
    String documentNumber,
    String email,
    Boolean hasBaggage
) {
}
