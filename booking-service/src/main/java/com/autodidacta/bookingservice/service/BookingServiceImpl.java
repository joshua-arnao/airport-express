package com.autodidacta.bookingservice.service;

import com.autodidacta.bookingservice.client.ScheduleClient;
import com.autodidacta.bookingservice.dto.*;
import com.autodidacta.bookingservice.entity.*;
import com.autodidacta.bookingservice.repository.BookingRepository;
import com.autodidacta.bookingservice.repository.PassengerRepository;
import com.autodidacta.bookingservice.repository.TicketRepository;
import com.autodidacta.bookingservice.shared.exceptions.TripNotAvailableException;
import com.autodidacta.scheduleservice.entity.TripStatus;
import com.autodidacta.scheduleservice.shared.exceptions.InsufficientSeatsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{
    private final BookingRepository bookingRepository;
    private final ScheduleClient scheduleClient;
    private final TicketRepository ticketRepository;
    private final PassengerRepository passengerRepository;

    @Override
    public BookingResponse createBooking(BookingRequest bookingRequest) {
        TripClientResponse trip = scheduleClient.getTripById(bookingRequest.tripId());

        if (!trip.status().equals(TripStatus.SCHEDULED)) {
            throw new TripNotAvailableException("Trip is not available");
        }

        int requestedSeats = bookingRequest.passengersRequest().size();
        if (requestedSeats > trip.availableSeats()) {
            throw new InsufficientSeatsException("Not enough seats");
        }

        BigDecimal totalAmount = trip.price().multiply(BigDecimal.valueOf(requestedSeats));

        Booking booking = Booking.builder()
                .tripId(bookingRequest.tripId())
                .totalAmount(totalAmount)
                .bookingStatus(BookingStatus.PENDING)
                .stripePaymentId(null)
                .build();

        Booking bookingSaved = bookingRepository.save(booking);

        List<TicketResponse> ticketResponses = bookingRequest.passengersRequest().stream()
                .map(passengerRequest -> {
                    Ticket ticket = Ticket.builder()
                            .bookingId(bookingSaved.getBookingId())
                            .qrToken(UUID.randomUUID().toString())
                            .ticketStatus(TicketStatus.ACTIVE)
                            .build();
                    Ticket savedTicket = ticketRepository.save(ticket);

                    Passenger passenger = Passenger.builder()
                            .ticketId(savedTicket.getTicketId())
                            .firstName(passengerRequest.firstName())
                            .lastName(passengerRequest.lastName())
                            .documentType(passengerRequest.documentType())
                            .documentNumber(passengerRequest.documentNumber())
                            .email(passengerRequest.email())
                            .hasBaggage(passengerRequest.hasBaggage())
                            .build();
                    passengerRepository.save(passenger);

                    return new TicketResponse(
                            savedTicket.getTicketId(),
                            savedTicket.getBookingId(),
                            savedTicket.getQrToken(),
                            savedTicket.getTicketStatus()
                    );
                })
                .toList();


        List<PassengerResponse> passengerResponses = passengerRepository
                .findAllByTicketIdIn(ticketResponses.stream()
                        .map(TicketResponse::ticketId)
                        .toList())
                .stream()
                .map(p -> new PassengerResponse(
                        p.getPassengerId(),
                        p.getTicketId(),
                        p.getFirstName(),
                        p.getLastName(),
                        p.getDocumentType(),
                        p.getDocumentNumber(),
                        p.getEmail(),
                        p.getHasBaggage()
                ))
                .toList();

        return new BookingResponse(
                bookingSaved.getBookingId(),
                bookingSaved.getTotalAmount(),
                bookingSaved.getBookingStatus(),
                bookingSaved.getStripePaymentId(),
                passengerResponses,
                ticketResponses
        );
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
