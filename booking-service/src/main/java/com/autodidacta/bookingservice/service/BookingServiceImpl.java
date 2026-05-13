package com.autodidacta.bookingservice.service;

import com.autodidacta.bookingservice.client.ScheduleClient;
import com.autodidacta.bookingservice.dto.*;
import com.autodidacta.bookingservice.entity.*;
import com.autodidacta.bookingservice.repository.BookingRepository;
import com.autodidacta.bookingservice.repository.PassengerRepository;
import com.autodidacta.bookingservice.repository.TicketRepository;
import com.autodidacta.bookingservice.shared.exceptions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ScheduleClient scheduleClient;
    private final TicketRepository ticketRepository;
    private final PassengerRepository passengerRepository;
    private final QrService qrService;

    @Override
    public BookingResponse createBooking(BookingRequest bookingRequest) {
        TripClientResponse trip = scheduleClient.getTripById(bookingRequest.tripId());

        if (!"SCHEDULED".equals(trip.status())) {
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

        bookingRequest.passengersRequest().forEach(passengerRequest -> {
            Ticket ticket = Ticket.builder()
                    .bookingId(bookingSaved.getBookingId())
                    .ticketStatus(TicketStatus.ACTIVE)
                    .build();
            Ticket savedTicket = ticketRepository.save(ticket);

            String qrToken = qrService.generateQrToken(
                    savedTicket.getTicketId(),
                    bookingRequest.tripId(),
                    bookingRequest.stopId()
            );

            savedTicket.setQrToken(qrToken);
            ticketRepository.save(savedTicket);

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
        });

        return toBookingResponse(bookingSaved);
    }

    @Override
    public BookingResponse getBookingById(UUID bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException("Booking not Found"));

        return toBookingResponse(booking);
    }

    @Override
    public BookingResponse confirmBooking(UUID bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException("Booking not found"));
        booking.confirm();

        bookingRepository.save(booking);

        return toBookingResponse(booking);
    }

    @Override
    public BookingResponse cancelBooking(UUID bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException("Booking not found"));
        booking.cancel();

        bookingRepository.save(booking);

        return toBookingResponse(booking);
    }

    @Override
    public List<BookingResponse> getBookingsByTripId(UUID tripId) {
        List<Booking> bookings = bookingRepository.findByTripId(tripId);

        return bookings.stream()
                .map(this::toBookingResponse)
                .toList();
    }

    @Override
    public BookingResponse getBookingByEmailAndBookingId(String email, UUID bookingId) {
        Passenger passenger = passengerRepository.findByEmail(email).orElseThrow(() -> new PassengerNotFoundException("Passenger not found"));
        Ticket ticket =  ticketRepository.findByTicketId(passenger.getTicketId()).orElseThrow(() -> new TicketNotFoundException("Ticket not found"));

        if (!ticket.getBookingId().equals(bookingId)) {
            throw new BookingNotFoundException("Booking not found");
        }

        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException("Booking not found"));

        return toBookingResponse(booking);
    }

    private PassengerResponse toPassengerResponse(Passenger passenger) {
        return new PassengerResponse(
                passenger.getPassengerId(),
                passenger.getTicketId(),
                passenger.getFirstName(),
                passenger.getLastName(),
                passenger.getDocumentType(),
                passenger.getDocumentNumber(),
                passenger.getEmail(),
                passenger.getHasBaggage()
        );
    }

    private TicketResponse toTicketResponse(Ticket ticket) {
        return new TicketResponse(
                ticket.getTicketId(),
                ticket.getBookingId(),
                ticket.getQrToken(),
                ticket.getTicketStatus()
        );
    }

    private BookingResponse toBookingResponse(Booking booking) {
        List<Ticket> tickets = ticketRepository.findByBookingId(booking.getBookingId());
        List<TicketResponse> ticketResponses = tickets.stream()
                .map(this::toTicketResponse)
                .toList();
        List<PassengerResponse> passengerResponses = passengerRepository
                .findAllByTicketIdIn(tickets.stream()
                        .map(Ticket::getTicketId)
                        .toList())
                .stream()
                .map(this::toPassengerResponse)
                .toList();

        return new BookingResponse(
                booking.getBookingId(),
                booking.getTotalAmount(),
                booking.getBookingStatus(),
                booking.getStripePaymentId(),
                passengerResponses,
                ticketResponses
        );
    }
}
