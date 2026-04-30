package com.autodidacta.paymentservice.dto;

import java.util.List;
import java.util.UUID;

public record BookingClientResponse(
        UUID bookingId,
        String bookingStatus,
        List<TicketClientResponse> tickets
) {
}
