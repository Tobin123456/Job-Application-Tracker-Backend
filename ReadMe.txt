# Application-Tracker Backend

A Spring Boot backend for managing and visualizing job applications. This project focuses on the backend API and follows an enterprise-grade architecture.

---

## Tech Stack

**Backend:**
- Java 21+
- Spring Boot, Spring MVC (REST API)
- Spring Data JPA / Hibernate
- PostgreSQL
- Flyway (database migrations)
- Maven

---

## Features

- CRUD operations for job applications
- Track application status (APPLIED, INTERVIEW, OFFER, REJECTED)
- Manage companies and jobs
- Input validation & global error handling
- Database versioning via Flyway

---


## Database

- PostgreSQL with Flyway-managed schema
- JPA/Hibernate for persistence and entity mapping

---

## Getting Started (Local Development)

**Prerequisites:**
- Java 21+
- PostgreSQL

**Backend:**
1. Set up the PostgreSQL database and run migrations with Flyway.
2. Run the Spring Boot backend with your IDE or via command line:
   ```bash
   ./mvnw spring-boot:run

---

## API Documentation

- OpenAPI / Swagger documentation available at:
[http://localhost:8080/swagger-ui/index.html#/application-controller](http://localhost:8080/swagger-ui/index.html#/application-controller)

---

## Testing

- Unit tests for services
- Controller tests for REST endpoints
- Integration tests with PostgreSQL

---

## Project Goals

- Demonstrate clean Spring Boot architecture
- Apply enterprise-grade patterns & best practices
- Build a realistic, maintainable backend API

