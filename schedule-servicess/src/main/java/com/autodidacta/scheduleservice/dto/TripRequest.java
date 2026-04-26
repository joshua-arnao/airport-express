package com.autodidacta.scheduleservice.dto;

import java.time.LocalDate;
import java.util.UUID;

public record TripRequest(
        UUID scheduleId,
        LocalDate date
) {
}
