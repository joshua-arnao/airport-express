package com.autodidacta.bookingservice.dto;

import java.util.UUID;

public record PassengerClientResponse(
        UUID passengerId,
        String firstName,
        String lastName,
        Boolean hasBaggage
) {
}
