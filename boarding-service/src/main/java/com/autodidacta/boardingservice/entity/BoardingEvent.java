package com.autodidacta.boardingservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "boardings")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class BoardingEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    @Column(nullable = false)
    private UUID eventId;

    @Column(nullable = false)
    private UUID ticketId;

    @Column(nullable = false)
    private UUID tripId;

    @Column(nullable = false)
    private UUID stopId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResultScanner resultScanner;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime scannedAt;

}
