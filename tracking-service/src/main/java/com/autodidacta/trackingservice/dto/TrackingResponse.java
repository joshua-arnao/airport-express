package com.autodidacta.trackingservice.dto;

import com.autodidacta.trackingservice.entity.PositionStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TrackingResponse(
        UUID tripId,
        UUID currentStopId,
        PositionStatus positionStatus,
        LocalDateTime updatedAt
) {
}
