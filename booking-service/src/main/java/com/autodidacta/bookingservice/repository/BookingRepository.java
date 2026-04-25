package com.autodidacta.bookingservice.repository;

import com.autodidacta.bookingservice.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {
    List<Booking> findByTripId(UUID tripId);
}
