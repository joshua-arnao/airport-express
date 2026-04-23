package com.autodidacta.scheduleservice.entity;

import com.autodidacta.scheduleservice.shared.exceptions.InsufficientSeatsException;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "airport_express_trips")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    @Column(nullable = false)
    private UUID tripId;

    @Version
    private Long version;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private Integer bookedSeats;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TripStatus status;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public void bookSeat(Integer quantity){
        Integer availableSeats = this.capacity - this.bookedSeats;

        if (quantity <= availableSeats) {
            //this.bookedSeats = this.bookedSeats - quantity;
            this.bookedSeats += quantity;
        } else {
            throw new InsufficientSeatsException("Not enough seats available");
        }
    }
}
