package com.autodidacta.bookingservice.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record TripClientResponse(
        UUID tripId,
        Integer availableSeats,
        String status,
        BigDecimal price
) {
}
