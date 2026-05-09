package com.autodidacta.trackingservice.dto;

import java.util.UUID;

public record TrackingUpdateRequest(
        UUID tripId,
        UUID currentStopId
) {
}
