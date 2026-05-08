package com.autodidacta.boardingservice.controller;

import com.autodidacta.boardingservice.dto.ScanRequest;
import com.autodidacta.boardingservice.dto.ScanResponse;
import com.autodidacta.boardingservice.service.BoardingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/boardings")
@RequiredArgsConstructor
public class BoardingController {
    private final BoardingService boardingService;

    @PostMapping
    public ScanResponse scanQr(@RequestBody ScanRequest scanRequest) {
        return boardingService.scanQr(scanRequest);
    }

    @GetMapping("/list/{ticketId}")
    public List<ScanResponse> getScanHistory(@PathVariable UUID ticketId) {
        return boardingService.getScanHistory(ticketId);
    }
}
