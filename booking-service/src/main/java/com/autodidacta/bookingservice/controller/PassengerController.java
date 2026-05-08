package com.autodidacta.bookingservice.controller;

import com.autodidacta.bookingservice.dto.PassengerResponse;
import com.autodidacta.bookingservice.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/passengers")
@RequiredArgsConstructor
public class PassengerController {
    private final PassengerService passengerService;

    @GetMapping("/api/tickets/{ticketId}/passenger")
    public PassengerResponse getPassengerByTicketId(@PathVariable UUID ticketId) {
        return passengerService.getPassengerByTicketId(ticketId);
    }
}
