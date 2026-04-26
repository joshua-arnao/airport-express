package com.autodidacta.scheduleservice.dto;

import java.time.LocalTime;
import java.util.UUID;

public record ScheduleRequest(
        UUID routeId,
        LocalTime departureTime,
        LocalTime arrivalTime
) {
}
