package com.autodidacta.trackingservice.service;

import com.autodidacta.trackingservice.dto.TrackingResponse;
import com.autodidacta.trackingservice.dto.TrackingUpdateRequest;
import com.autodidacta.trackingservice.entity.BusPosition;
import com.autodidacta.trackingservice.entity.PositionStatus;
import com.autodidacta.trackingservice.repository.BusPositionRepository;
import com.autodidacta.trackingservice.shared.exceptions.BusPositionNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrackingServiceImpl implements TrackingService {
    private final BusPositionRepository busPositionRepository;

    @Override
    public TrackingResponse getPosition(UUID tripId) {
        BusPosition busPosition = busPositionRepository.findByTripId(tripId).orElseThrow(() -> new BusPositionNotFoundException("Position not found"));
        return new TrackingResponse(
                busPosition.getTripId(),
                busPosition.getCurrentStopId(),
                busPosition.getPositionStatus(),
                busPosition.getUpdatedAt()
        );
    }

    @Override
    public TrackingResponse updatePosition(TrackingUpdateRequest request) {
        BusPosition busPosition = busPositionRepository.findByTripId(request.tripId())
                .orElse(BusPosition.builder()
                        .tripId(request.tripId())
                        .positionStatus(PositionStatus.AT_STOP)
                        .build());

        busPosition.setCurrentStopId(request.currentStopId());
        busPosition.setPositionStatus(PositionStatus.AT_STOP);

        BusPosition saved = busPositionRepository.save(busPosition);

        return new TrackingResponse(
                saved.getTripId(),
                saved.getCurrentStopId(),
                saved.getPositionStatus(),
                saved.getUpdatedAt()
        );
    }
}
