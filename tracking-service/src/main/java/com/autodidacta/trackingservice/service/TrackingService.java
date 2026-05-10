package com.autodidacta.trackingservice.service;

import com.autodidacta.trackingservice.dto.TrackingResponse;
import com.autodidacta.trackingservice.dto.TrackingUpdateRequest;

import java.util.UUID;

public interface TrackingService {
    TrackingResponse getPosition(UUID tripId);
    TrackingResponse updatePosition(TrackingUpdateRequest request);
}
