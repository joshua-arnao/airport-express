package com.autodidacta.bookingservice.service;

import com.autodidacta.bookingservice.dto.TicketResponse;

import java.util.UUID;

public interface TicketService {
    TicketResponse getTicketById(UUID ticketId);
    TicketResponse markAsUsed(UUID ticketId);
}
