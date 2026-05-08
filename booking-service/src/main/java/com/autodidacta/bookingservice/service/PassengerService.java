package com.autodidacta.bookingservice.service;

import com.autodidacta.bookingservice.dto.PassengerResponse;

import java.util.UUID;

public interface PassengerService {
    PassengerResponse getPassengerByTicketId(UUID ticketId);
}
