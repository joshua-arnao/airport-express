<p align="center">
    <b>Select Language:</b><br>
    <a href="README.md">🇺🇸 English</a> |
    <a href="README.sp.md">🇪🇸 Español</a>
</p>

---

# Upgrade Airport Express Lima — Backend Platform

A real-world microservices platform built to solve a real problem: the Airport Express Lima bus service between Jorge Chávez International Airport and Miraflores had no real ticket validation system.

---

## The Problem

When using Airport Express Lima, the boarding assistant only **took a photo** of the QR code shown on the passenger's screen — there was no real validation. This created serious operational and security issues:

| Problem | Impact |
|---|---|
| QR never actually validated | Same ticket could be used multiple times |
| No capacity control per trip | Overbooking was possible |
| Boarding pass only on web | No email confirmation, no digital wallet |
| Manual photo validation | Insecure and imprecise |
| No real-time bus tracking | Passengers had no way to know where the bus was |

This project replaces that manual process with a **first-world digital ticketing platform**, inspired by systems used by Latam Airlines, Renfe (Spain), and JR Pass (Japan).

---

## Architecture — Microservices with DDD

Built as a **Maven multi-module monorepo** with 7 independent Spring Boot microservices, each with its own PostgreSQL database.

```
airport-express/
  ├── api-gateway/         → Single entry point — routes all requests    :8080
  ├── identity-service/    → Authentication, JWT, roles                  :8081
  ├── schedule-service/    → Routes, stops, schedules, trips             :8082
  ├── booking-service/     → Reservations, tickets, QR generation        :8083
  ├── payment-service/     → Stripe payment processing                   :8084
  ├── boarding-service/    → QR validation at boarding                   :8085
  └── tracking-service/    → Real-time bus position                      :8086
```

### Why Microservices?

Each bounded context is independently deployable, scalable, and isolated from failures. If `payment-service` goes down, passengers can still check schedules. This is the architecture used by Uber, Grab, and all major transport platforms.

### API Gateway

All frontend requests go through a single entry point on port `8080`. The gateway routes to the appropriate microservice — the client never needs to know about 7 different ports.

### Inter-service Communication

- **Synchronous (Feign Client)** — when an immediate response is required. Example: `booking-service` calls `schedule-service` to verify seat availability before confirming a reservation.
- **Asynchronous** — reserved for future event-driven expansion with RabbitMQ.

### Circuit Breaker (Resilience4j)

If a downstream service fails repeatedly, the circuit opens and returns a controlled error immediately — preventing cascade failures. The same pattern used by Netflix and Amazon.

---

## Key Technical Decisions

### QR Tokens as JWT — Never Exposing Personal Data

The boarding QR is a **signed JWT** containing only IDs — never names, document numbers, or baggage information. The boarding assistant's device decodes it, verifies the cryptographic signature, and validates the ticket status with `booking-service`.

```json
{
  "ticketId": "uuid",
  "tripId": "uuid",
  "stopId": "uuid"
}
```

This directly solves the baggage security problem from the original system.

### One QR Per Passenger

Each passenger gets their own unique JWT-signed QR token — enabling individual validation and preventing ticket sharing.

### Optimistic Locking for Seat Reservation

`@Version` on the `Trip` entity guarantees only one booking succeeds when two passengers compete for the last seat simultaneously — preventing overbooking without database-level locks.

### Immutable Tickets

Once created, a `Ticket` never changes. Only `status` transitions (ACTIVE → USED) via `markAsUsed()`. No setters — enforced at the entity level.

### Roles: PASSENGER, CONDUCTOR, ADMIN

- **PASSENGER** — registers publicly, logs in by phone number
- **CONDUCTOR** — created by admin, logs in by email, validates QR at boarding stops
- **ADMIN** — manages routes, schedules, trips, and conductors

### Stripe Payment Integration

Payment processing never touches card data — Stripe handles it through `PaymentIntent`. The platform confirms payment and sends boarding passes by email.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3.4 |
| Security | Spring Security + JWT (JJWT 0.12.3) |
| ORM | Spring Data JPA / Hibernate |
| Database | PostgreSQL (one per service) |
| Service Communication | OpenFeign |
| Payments | Stripe Java SDK |
| Build | Maven Multi-module Monorepo |
| Infrastructure | Docker Compose |

---

## Complete Booking Flow

```
1. Passenger selects available trip            → schedule-service
2. Creates booking with passenger data         → booking-service
   ↳ Validates seat availability               → Feign → schedule-service
   ↳ Generates JWT-signed QR per passenger
   ↳ Booking created in PENDING status
3. Processes payment                           → payment-service
   ↳ Creates Stripe PaymentIntent
   ↳ On success → confirms booking             → Feign → booking-service
   ↳ Boarding passes sent by email
4. Boarding assistant scans QR at stop         → boarding-service
   ↳ Decodes JWT — verifies signature
   ↳ Validates ticket status                   → Feign → booking-service
   ↳ Marks ticket as USED
   ↳ Records immutable BoardingEvent
5. Bus position updates on each scan           → tracking-service
```

---

## Running Locally

**Prerequisites:** Java 21, Docker Desktop, Maven 3.9+

```bash
docker-compose up -d
cd identity-service && ./mvnw spring-boot:run
```

Configure each service using the `.example` files in `src/main/resources/`.
