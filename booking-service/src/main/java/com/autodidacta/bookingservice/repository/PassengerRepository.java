package com.autodidacta.bookingservice.repository;

import com.autodidacta.bookingservice.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PassengerRepository extends JpaRepository<Passenger, UUID> {
    List<Passenger> findByTicketId(UUID ticketId);
    Optional<Passenger> findByEmail(String email);
}
