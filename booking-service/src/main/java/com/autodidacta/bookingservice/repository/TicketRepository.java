package com.autodidacta.bookingservice.repository;

import com.autodidacta.bookingservice.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    List<Ticket> findByBookingId(UUID bookingId);
    Optional<Ticket> findByTicketId(UUID ticketId);
}
