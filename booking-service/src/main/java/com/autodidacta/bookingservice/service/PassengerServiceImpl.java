package com.autodidacta.bookingservice.service;

import com.autodidacta.bookingservice.dto.PassengerResponse;
import com.autodidacta.bookingservice.entity.Passenger;
import com.autodidacta.bookingservice.repository.PassengerRepository;
import com.autodidacta.bookingservice.shared.exceptions.PassengerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements  PassengerService {
    private final PassengerRepository passengerRepository;

    @Override
    public PassengerResponse getPassengerByTicketId(UUID ticketId) {
        Passenger passenger = passengerRepository.findByTicketId(ticketId)
                .orElseThrow(() -> new PassengerNotFoundException("Passenger not found"));
        return toPassengerResponse(passenger);
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
}
