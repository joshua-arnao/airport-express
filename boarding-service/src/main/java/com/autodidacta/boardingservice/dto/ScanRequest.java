package com.autodidacta.boardingservice.dto;

import java.util.UUID;

public record ScanRequest(
        String qrToken,
        UUID tripId,
        UUID stopId
) {
}
