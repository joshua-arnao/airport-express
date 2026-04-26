package com.autodidacta.scheduleservice.dto;

import java.time.LocalTime;
import java.util.UUID;

public record ScheduleResponse(
        UUID scheduleId,
        RouteResponse routeResponse,
        LocalTime departureTime,
        LocalTime arrivalTime
) {
}
