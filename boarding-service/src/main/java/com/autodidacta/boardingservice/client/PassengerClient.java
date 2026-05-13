package com.autodidacta.boardingservice.client;

import com.autodidacta.bookingservice.dto.PassengerClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "passenger-client", url = "http://localhost:8083")
public interface PassengerClient {
    @GetMapping("/api/tickets/{ticketId}/passenger")
    PassengerClientResponse getPassengerByTicketId(@PathVariable UUID ticketId);
}
