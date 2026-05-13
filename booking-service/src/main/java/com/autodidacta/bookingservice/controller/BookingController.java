package com.autodidacta.bookingservice.controller;

import com.autodidacta.bookingservice.dto.BookingRequest;
import com.autodidacta.bookingservice.dto.BookingResponse;
import com.autodidacta.bookingservice.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public BookingResponse createBooking(@Valid @RequestBody BookingRequest bookingRequest) {
        return bookingService.createBooking(bookingRequest);
    }

    @GetMapping("/{bookingId}")
    public BookingResponse getBookingById(@PathVariable UUID bookingId) {
        return bookingService.getBookingById(bookingId);
    }

    @PutMapping("/{bookingId}/confirm")
    public BookingResponse confirmBooking(@PathVariable UUID bookingId) {
        return bookingService.confirmBooking(bookingId);
    }

    @PutMapping("/{bookingId}/cancel")
    public BookingResponse cancelBooking(@PathVariable UUID bookingId) {
        return bookingService.cancelBooking(bookingId);
    }

    @GetMapping("/list/{tripId}")
    public List<BookingResponse> getBookingsByTripId(@PathVariable UUID tripId) {
        return bookingService.getBookingsByTripId(tripId);
    }

    @GetMapping("/search")
    public BookingResponse getBookingByEmailAndBookingId(@RequestParam String email, @RequestParam UUID bookingId) {
        return bookingService.getBookingByEmailAndBookingId(email, bookingId);
    }

    @GetMapping("/{tripId}")
    public TripResponse getTripById(@PathVariable UUID tripId) {
        return tripService.getTripById(tripId);
    }
}
