package com.autodidacta.bookingservice.dto;

import com.autodidacta.bookingservice.entity.BookingStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record BookingResponse(
        UUID bookingId,
        BigDecimal totalAmount,
        BookingStatus bookingStatus,
        String stripePaymentId,
        List<PassengerResponse> passengersResponse,
        List<TicketResponse> ticketsResponse
){
}
