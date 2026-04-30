package com.autodidacta.paymentservice.dto;

import java.util.UUID;

public record TicketClientResponse(
        UUID ticketId,
        String qrToken,
        String ticketStatus
) {
}
