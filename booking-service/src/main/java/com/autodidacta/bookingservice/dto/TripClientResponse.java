package com.autodidacta.bookingservice.dto;

import com.autodidacta.scheduleservice.entity.TripStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record TripClientResponse(
        UUID tripId,
        Integer availableSeats,
        TripStatus status,
        BigDecimal price
) {
}
