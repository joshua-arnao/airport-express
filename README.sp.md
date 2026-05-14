<p align="center">
    <b>Seleccionar Lenguaje:</b><br>
    <a href="README.md">🇺🇸 English</a> |
    <a href="README.sp.md">🇪🇸 Español</a>
</p>

# Upgrade Airport Express Lima — Plataforma Backend

Una plataforma de microservicios construida para resolver un problema real: el servicio de bus Airport Express Lima entre el Aeropuerto Internacional Jorge Chávez y Miraflores no tenía un sistema real de validación de tickets.

---

## El Problema

Al usar el servicio Airport Express Lima, el asistente de abordaje solo **tomaba una foto** del QR mostrado en la pantalla del pasajero — no había validación real. Esto generaba graves problemas operativos y de seguridad:

| Problema | Impacto |
|---|---|
| QR nunca validado realmente | El mismo ticket podía usarse múltiples veces |
| Sin control de capacidad por viaje | El overbooking era posible |
| Comprobante solo en web | Sin email de confirmación, sin wallet digital |
| Validación manual con foto | Insegura e imprecisa |
| Sin tracking del bus en tiempo real | El pasajero no tenía forma de saber dónde estaba el bus |

Este proyecto reemplaza ese proceso manual con una **plataforma de tickets de primer mundo**, inspirada en sistemas de Latam Airlines, Renfe (España) y JR Pass (Japón).

---

## Arquitectura — Microservicios con DDD

Construido como un **monorepo multi-módulo Maven** con 7 microservicios Spring Boot independientes, cada uno con su propia base de datos PostgreSQL.

```
airport-express/
  ├── api-gateway/         → Punto de entrada único — enruta todas las peticiones  :8080
  ├── identity-service/    → Autenticación, JWT, roles                             :8081
  ├── schedule-service/    → Rutas, paraderos, horarios, viajes                    :8082
  ├── booking-service/     → Reservas, tickets, generación de QR                  :8083
  ├── payment-service/     → Procesamiento de pagos con Stripe                    :8084
  ├── boarding-service/    → Validación de QR en el abordaje                      :8085
  └── tracking-service/    → Posición del bus en tiempo real                      :8086
```

### ¿Por qué Microservicios?

Cada bounded context tiene despliegue, escalabilidad y aislamiento de fallos independiente. Si `payment-service` cae, los pasajeros pueden seguir consultando horarios. Esta es la arquitectura usada por Uber, Grab y todas las plataformas de transporte de primer nivel.

### API Gateway

Todas las peticiones del frontend pasan por un único punto de entrada en el puerto `8080`. El gateway enruta al microservicio correspondiente — el cliente nunca necesita conocer 7 puertos diferentes.

### Comunicación entre Servicios

- **Síncrona (Feign Client)** — cuando se necesita respuesta inmediata. Ejemplo: `booking-service` llama a `schedule-service` para verificar disponibilidad de asientos antes de confirmar una reserva.
- **Asíncrona** — reservada para expansión event-driven futura con RabbitMQ.

### Circuit Breaker (Resilience4j)

Si un servicio falla repetidamente, el circuito se abre y retorna un error controlado inmediatamente — evitando fallos en cascada. El mismo patrón usado por Netflix y Amazon.

---

## Decisiones Técnicas Clave

### QR Tokens como JWT — Sin Exponer Datos Personales

El QR de abordaje es un **JWT firmado** que contiene solo IDs — nunca nombres, números de documento ni información de equipaje. El dispositivo del asistente de abordaje lo decodifica, verifica la firma criptográfica y valida el estado del ticket con `booking-service`.

```json
{
  "ticketId": "uuid",
  "tripId": "uuid",
  "stopId": "uuid"
}
```

Esto resuelve directamente el problema de seguridad del sistema original — ningún dato personal viaja en el QR.

### Un QR por Pasajero

Cada pasajero recibe su propio QR único firmado con JWT — habilitando validación individual y previniendo el uso compartido de tickets.

### Optimistic Locking para Reserva de Asientos

`@Version` en la entidad `Trip` garantiza que solo una reserva tenga éxito cuando dos pasajeros compiten por el último asiento simultáneamente — previniendo overbooking sin locks a nivel de base de datos.

### Tickets Inmutables

Una vez creado, un `Ticket` nunca cambia. Solo el `status` transiciona (ACTIVE → USED) mediante `markAsUsed()`. Sin setters — enforzado a nivel de entidad.

### Roles: PASSENGER, CONDUCTOR, ADMIN

- **PASSENGER** — se registra públicamente, hace login con número de teléfono
- **CONDUCTOR** — creado por el admin, hace login con email, valida QR en los paraderos
- **ADMIN** — gestiona rutas, horarios, viajes y conductores

### Integración de Pagos con Stripe

El procesamiento de pagos nunca toca los datos de la tarjeta — Stripe lo maneja mediante `PaymentIntent`. La plataforma confirma el pago y envía los boarding passes por email.

---

## Stack Tecnológico

| Capa | Tecnología |
|---|---|
| Lenguaje | Java 21 |
| Framework | Spring Boot 3.4 |
| Seguridad | Spring Security + JWT (JJWT 0.12.3) |
| ORM | Spring Data JPA / Hibernate |
| Base de datos | PostgreSQL (una por servicio) |
| Comunicación entre servicios | OpenFeign |
| Pagos | Stripe Java SDK |
| Build | Maven Multi-module Monorepo |
| Infraestructura | Docker Compose |

---

## Flujo Completo de Reserva

```
1. Pasajero selecciona viaje disponible        → schedule-service
2. Crea reserva con datos de pasajeros         → booking-service
   ↳ Valida disponibilidad de asientos         → Feign → schedule-service
   ↳ Genera QR JWT firmado por pasajero
   ↳ Reserva creada en estado PENDING
3. Procesa pago                                → payment-service
   ↳ Crea PaymentIntent en Stripe
   ↳ Al éxito → confirma reserva              → Feign → booking-service
   ↳ Boarding passes enviados por email
4. Asistente de abordaje escanea QR            → boarding-service
   ↳ Decodifica JWT — verifica firma
   ↳ Valida estado del ticket                  → Feign → booking-service
   ↳ Marca ticket como USED
   ↳ Registra BoardingEvent inmutable
5. Posición del bus actualiza en cada escaneo  → tracking-service
```

---

## Levantar Localmente

**Requisitos:** Java 21, Docker Desktop, Maven 3.9+

```bash
docker-compose up -d
cd identity-service && ./mvnw spring-boot:run
```

Configura cada servicio usando los archivos `.example` en `src/main/resources/`.
