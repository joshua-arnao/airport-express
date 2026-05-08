package com.autodidacta.bookingservice.dto;

import java.util.List;
import java.util.UUID;

public record BookingRequest(
        UUID tripId,
        UUID stopId,
        List<PassengerRequest> passengersRequest
        ) {
}
