package com.autodidacta.bookingservice.service;

import com.autodidacta.bookingservice.dto.TicketResponse;
import com.autodidacta.bookingservice.entity.Ticket;
import com.autodidacta.bookingservice.entity.TicketStatus;
import com.autodidacta.bookingservice.repository.TicketRepository;
import com.autodidacta.bookingservice.shared.exceptions.TicketNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;

    @Override
    public TicketResponse getTicketById(UUID ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found"));
        return new TicketResponse(
                ticket.getTicketId(),
                ticket.getBookingId(),
                ticket.getQrToken(),
                ticket.getTicketStatus()
        );
    }

    @Override
    public TicketResponse markAsUsed(UUID ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found"));
        ticket.markAsUsed();
        ticketRepository.save(ticket);
        return new TicketResponse(
                ticket.getTicketId(),
                ticket.getBookingId(),
                ticket.getQrToken(),
                ticket.getTicketStatus()
        );
    }
}
