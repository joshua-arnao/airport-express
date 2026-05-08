package com.autodidacta.boardingservice.client;

import com.autodidacta.boardingservice.dto.TicketClientResponse;
import com.autodidacta.bookingservice.dto.PassengerClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.UUID;

@FeignClient(name = "booking-service")
public interface TicketClient {
    @GetMapping("/api/tickets/{ticketId}")
    TicketClientResponse getTicketById(@PathVariable UUID ticketId);

    @PutMapping("/api/tickets/{ticketId}/use")
    TicketClientResponse markAsUsed(@PathVariable UUID ticketId);
}
