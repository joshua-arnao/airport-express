package com.autodidacta.boardingservice.shared.error;

import com.autodidacta.boardingservice.shared.exceptions.TicketInvalidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TicketInvalidException.class)
    public ResponseEntity<ErrorResponse> handleTicketInvalid(TicketInvalidException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(401, ex.getMessage(), LocalDateTime.now()));
    }
}
