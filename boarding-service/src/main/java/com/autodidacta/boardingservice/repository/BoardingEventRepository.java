package com.autodidacta.boardingservice.repository;

import com.autodidacta.boardingservice.dto.ScanRequest;
import com.autodidacta.boardingservice.dto.ScanResponse;
import com.autodidacta.boardingservice.entity.BoardingEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BoardingEventRepository extends JpaRepository<BoardingEvent, UUID> {
    List<BoardingEvent> findByTripId(UUID tripId);
    Optional <BoardingEvent> findByTicketId(UUID ticketId);
}
