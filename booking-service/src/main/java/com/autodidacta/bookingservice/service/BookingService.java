package com.autodidacta.bookingservice.service;

import com.autodidacta.bookingservice.dto.BookingRequest;
import com.autodidacta.bookingservice.dto.BookingResponse;

import java.util.List;
import java.util.UUID;

public interface BookingService {
    BookingResponse createBooking(BookingRequest bookingRequest);
    BookingResponse getBookingById(UUID bookingId);
    BookingResponse confirmBooking(UUID bookingId);
    BookingResponse cancelBooking(UUID bookingId);
    List<BookingResponse> getBookingsByTripId(UUID tripId);
    BookingResponse getBookingByEmailAndBookingId(String email, UUID bookingId);
}
