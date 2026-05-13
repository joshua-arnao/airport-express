package com.autodidacta.bookingservice.controller;

import com.autodidacta.bookingservice.dto.PassengerResponse;
import com.autodidacta.bookingservice.dto.TicketResponse;
import com.autodidacta.bookingservice.service.PassengerService;
import com.autodidacta.bookingservice.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    private final PassengerService passengerService;

    @GetMapping("/{ticketId}")
    public TicketResponse getTicketById(@PathVariable UUID ticketId) {
        return ticketService.getTicketById(ticketId);
    }

    @PutMapping("/{ticketId}/use")
    public TicketResponse markAsUsed(@PathVariable UUID ticketId) {
        return ticketService.markAsUsed(ticketId);
    }

    @GetMapping("/{ticketId}/passenger")
    public PassengerResponse getPassengerByTicketId(@PathVariable UUID ticketId) {
        return passengerService.getPassengerByTicketId(ticketId);
    }
}
