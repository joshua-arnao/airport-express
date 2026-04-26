package com.autodidacta.bookingservice.service;

import com.autodidacta.bookingservice.dto.BookingRequest;
import com.autodidacta.bookingservice.dto.BookingResponse;
import com.autodidacta.bookingservice.entity.Booking;
import com.autodidacta.bookingservice.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{
    private final BookingRepository bookingRepository;

    @Override
    public BookingResponse createBooking(BookingRequest bookingRequest) {
        List<Booking> trip = bookingRepository.findByTripId(bookingRequest.tripId());

        Booking booking = Booking.builder()
                .tripId()
                .totalAmount()
                .bookingStatus()
                .stripePaymentId()
                .build();
        return null;
    }

    @Override
    public BookingResponse getBookingById(UUID bookingId) {
        return null;
    }

    @Override
    public BookingResponse confirmBooking(UUID bookingId) {
        return null;
    }

    @Override
    public BookingResponse cancelBooking(UUID bookingId) {
        return null;
    }

    @Override
    public List<BookingResponse> getBookingsByTripId(UUID bookingId) {
        return List.of();
    }

    @Override
    public BookingResponse getBookingByEmailAndBookingId(String email, UUID bookingId) {
        return null;
    }
}
