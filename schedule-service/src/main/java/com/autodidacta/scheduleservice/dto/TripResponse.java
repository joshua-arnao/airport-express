package com.autodidacta.scheduleservice.dto;

import com.autodidacta.scheduleservice.entity.Schedule;
import com.autodidacta.scheduleservice.entity.TripStatus;

import java.time.LocalDate;
import java.util.UUID;

public record TripResponse(
        UUID tripId,
        ScheduleResponse scheduleResponse,
        LocalDate date,
        Integer capacity,
        Integer availableSeats,
        TripStatus status
) {
}
