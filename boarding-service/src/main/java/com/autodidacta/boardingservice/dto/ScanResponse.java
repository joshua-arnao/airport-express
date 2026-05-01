package com.autodidacta.boardingservice.dto;

public record ScanResponse(
        String result,
        String passengerName,
        Boolean hasBaggage,
        String message
) {
}
