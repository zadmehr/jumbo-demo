# Jumbo Geo Application

## Overview

Jumbo Geo Application is a Spring Boot application designed to manage stores and their locations using MongoDB as the database. It provides a REST API to retrieve the nearest stores based on geographic coordinates.

---

## Features

- **MongoDB Integration**: Connects to MongoDB to store and query store locations.
- **Rate Limiting**: Configured to limit API requests using `bucket4j`.
- **Swagger Documentation**: Provides API documentation through Swagger UI.
- **Logging**: Added SLF4J-based logging for better observability.
- **Spring Security**: Secures endpoints and allows fine-grained access control.
- **Actuator**: Exposes health check and application metrics.
- **Java 21 Compatibility**: Upgraded to leverage the latest Java features.
- **DTOs and Interfaces**: Implements clean code practices using DTOs and interfaces.

---

## Getting Started

### Prerequisites

Ensure you have the following installed:

- **Java 21**
- **Maven 3.8+**
- **Docker and Docker Compose**

---

### Build and Run

1. **Clean and package the application**:
    ```sh
    mvn clean install package
    ```

2. **Start the application using Docker Compose**:
    ```sh
    docker compose up -d
    ```

3. **Check Application Status**:
    Visit the following link in your browser:
    ```
    http://localhost:8080/actuator/health
    ```

4. **Access Swagger Documentation**:
    Visit the following link in your browser:
    ```
    http://localhost:8080/swagger-ui/index.html
    ```

5. **Test the API**:
    Fetch the nearest stores:
    ```
    http://localhost:8080/api/v1/nearest-stores?latitude=52.37867&longitude=4.883832
    ```


## Application Structure
1. ** St:
‍
```
src/main/java
├── com.jumbo.store.geo
│   ├── config       // Security and other configurations
│   ├── controller   // REST controllers
│   ├── dto          // Data Transfer Objects
│   ├── model        // MongoDB entity models
│   ├── repository   // Spring Data MongoDB repositories
│   ├── service      // Business logic services
│   └── service.impl // Service implementations
```

---

## Key Technologies Used

| Feature            | Technology/Tool         |
|---------------------|-------------------------|
| REST API Framework  | Spring Boot            |
| Database            | MongoDB                |
| Logging             | SLF4J                  |
| API Documentation   | Springdoc + Swagger UI |
| Security            | Spring Security        |
| Rate Limiting       | bucket4j           |
| Metrics and Health  | Spring Boot Actuator   |
| Docker Management   | Docker Compose         |

---

## Completed Features

- **MongoDB Initialization**:
  - Imported data via `init.js`.
  - Created geospatial index for `location`.

- **SLF4J Logging**:
  - Added logging to service methods for observability.

- **Rate Limiter**:
  - Restricted API calls to avoid abuse.

- **Swagger API Docs**:
  - Accessible at `/swagger-ui/index.html`.

- **Spring Security**:
  - Secured endpoints while allowing public access to specific APIs.

---

## Pending Features

- **Unit Tests**:
  - Add comprehensive unit test coverage for controllers and services.

- **Integration Tests**:
  - Validate integration between components.

- **CI/CD Pipeline**:
  - Automate build, test, and deployment workflows.

- **Secrets Management**:
  - Use Vault to manage sensitive MongoDB credentials.


---

## Contribution Guidelines

1. Fork the repository and clone it to your local machine.
2. Create a new branch for your feature or fix:
    ```sh
    git checkout -b feature-name
    ```
3. Make your changes and commit them with clear messages.
4. Push your changes to your forked repository:
    ```sh
    git push origin feature-name
    ```
5. Create a pull request to the main repository.

---

## Next Steps

1. **Testing**:
   - Write unit and integration tests.
   - Validate geospatial queries.

2. **Automation**:
   - Implement CI/CD pipeline using GitHub Actions or Jenkins.

3. **Production Readiness**:
   - Configure Vault for secure credential storage.
   - Optimize Docker image for smaller size.

---

For questions or suggestions, feel free to reach out!