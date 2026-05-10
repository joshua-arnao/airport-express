package com.autodidacta.trackingservice.repository;

import com.autodidacta.trackingservice.entity.BusPosition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BusPositionRepository extends JpaRepository<BusPosition, UUID> {
    Optional<BusPosition> findByTripId(UUID tripId);
}
