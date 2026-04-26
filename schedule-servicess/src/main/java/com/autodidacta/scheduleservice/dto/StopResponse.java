package com.autodidacta.scheduleservice.dto;

import java.util.UUID;

public record StopResponse(
        UUID stopId,
        String name
) {
}
