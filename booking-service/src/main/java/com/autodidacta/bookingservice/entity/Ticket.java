package com.autodidacta.bookingservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "airport_express_tickets")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    @Column(nullable = false)
    private UUID ticketId;

    @Column(nullable = false)
    private UUID bookingId;

    @Setter
    @Column(nullable = false)
    private String qrToken;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus ticketStatus;

    private LocalDateTime usedAt;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;
}
