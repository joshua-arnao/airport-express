package com.autodidacta.boardingservice.dto;

import java.util.UUID;

public record TicketClientResponse(
        UUID ticketId,
        UUID bookingId,
        String qrToken,
        String ticketStatus
) {
}
