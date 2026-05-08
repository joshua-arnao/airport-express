package com.autodidacta.boardingservice.service;

import com.autodidacta.boardingservice.dto.ScanRequest;
import com.autodidacta.boardingservice.dto.ScanResponse;

import java.util.List;
import java.util.UUID;

public interface BoardingService {
    ScanResponse scanQr(ScanRequest scanRequest);
    List<ScanResponse> getScanHistory(UUID ticketId);
}
