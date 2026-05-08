package com.autodidacta.boardingservice.service;

import com.autodidacta.boardingservice.client.PassengerClient;
import com.autodidacta.boardingservice.client.TicketClient;
import com.autodidacta.boardingservice.dto.ScanRequest;
import com.autodidacta.boardingservice.dto.ScanResponse;
import com.autodidacta.boardingservice.dto.TicketClientResponse;
import com.autodidacta.boardingservice.entity.BoardingEvent;
import com.autodidacta.boardingservice.entity.ResultScanner;
import com.autodidacta.boardingservice.repository.BoardingEventRepository;
import com.autodidacta.boardingservice.shared.exceptions.TicketInvalidException;
import com.autodidacta.bookingservice.dto.PassengerClientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardingServiceImpl implements BoardingService{
    private final BoardingEventRepository boardingEventRepository;
    private final TicketClient ticketClient;
    private final QrService qrService;
    private final PassengerClient passengerClient;

    @Override
    public ScanResponse scanQr(ScanRequest scanRequest) {
        UUID ticketId = qrService.extractTicketId(scanRequest.qrToken());
        TicketClientResponse ticket = ticketClient.getTicketById(ticketId);

        if (!"ACTIVE".equals(ticket.ticketStatus())) {
            throw new TicketInvalidException("El ticket no es válido o ya fue usado");
        }

        ticketClient.markAsUsed(ticketId);

        BoardingEvent boardingEvent = BoardingEvent.builder()
                .ticketId(ticketId)
                .tripId(scanRequest.tripId())
                .stopId(scanRequest.stopId())
                .resultScanner(ResultScanner.VALID)
                .build();

        boardingEventRepository.save(boardingEvent);
        PassengerClientResponse passenger = passengerClient.getPassengerByTicketId(ticketId);

        return new ScanResponse(
                "VALID",
                passenger.firstName() + " " + passenger.lastName(),
                passenger.hasBaggage(),
                "Boarding approved"
        );
    }

    @Override
    public List<ScanResponse> getScanHistory(UUID ticketId) {
        List<BoardingEvent> events = boardingEventRepository.findAllByTicketId(ticketId);
        PassengerClientResponse passenger = passengerClient.getPassengerByTicketId(ticketId);

        return events.stream()
                .map(event -> new ScanResponse(
                        event.getResultScanner().name(),
                        passenger.firstName() + " " + passenger.lastName(),
                        passenger.hasBaggage(),
                        "History record at stop: " + event.getStopId()
                ))
                .toList();
    }

}
