package com.autodidacta.bookingservice.dto;

import com.autodidacta.bookingservice.entity.TicketStatus;

import java.util.UUID;

public record TicketResponse(
        UUID ticketId,
        UUID bookingId,
        String qrToken,
        TicketStatus ticketStatus
) {
}
