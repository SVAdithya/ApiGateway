# API Gateway Service

## Purpose

This API Gateway service, built with Spring Cloud Gateway, acts as the central entry point for all client requests to the microservices ecosystem. Its primary responsibilities include:

*   **Single Entry Point:** Provides a unified interface for clients, abstracting the underlying microservice architecture.
*   **Request Routing:** Dynamically routes incoming requests to the appropriate downstream services based on configured paths or other criteria.
*   **Cross-Cutting Concerns:** Handles concerns common to multiple services, such as:
    *   **Rate Limiting:** Protects services from being overwhelmed by too many requests by integrating with the `rate-limiter` module.
*   **Extensibility:** Designed to be extended for other functionalities like:
    *   Security (Authentication/Authorization)
    *   SSL Termination
    *   Request/Response Transformation
    *   Logging and Monitoring

## Technologies Used

*   **Spring Boot:** For the underlying application framework and auto-configuration.
*   **Spring Cloud Gateway:** For providing the API Gateway functionality, including routing and filtering.

## Key Configurations (from `application.properties`)

*   **Server Port:** The service runs on port `8080`.
    ```properties
    server.port=8080
    ```

*   **Routes:**
    *   **`backend_service_alpha_route`**:
        *   Predicate: `Path=/alpha/**`
        *   Forwarded to: `http://localhost:9001`
        ```properties
        spring.cloud.gateway.routes[0].id=backend_service_alpha_route
        spring.cloud.gateway.routes[0].uri=http://localhost:9001
        spring.cloud.gateway.routes[0].predicates[0]=Path=/alpha/**
        ```
    *   **`cdn_simulation_route`**:
        *   Predicate: `Path=/cdn/**`
        *   Forwarded to: `http://localhost:8084`
        ```properties
        spring.cloud.gateway.routes[1].id=cdn_simulation_route
        spring.cloud.gateway.routes[1].uri=http://localhost:8084
        spring.cloud.gateway.routes[1].predicates[0]=Path=/cdn/**
        ```

*   **Rate Limiting:**
    *   Integrated via a `RateLimitingFilter` (a Spring Cloud Gateway `GlobalFilter`).
    *   This filter utilizes the `RateLimitingService` from the `rate-limiter` module.
    *   **Important:** The rate limiting feature requires a Redis instance to be running and accessible (default: `localhost:6379`), as it's used for storing request counts.

## How to Run

1.  **Ensure Prerequisites:**
    *   The root project (`my-app`) should ideally be built first (`mvn clean install` in the root directory) to ensure all dependencies, including the `rate-limiter` module, are available.
    *   A Redis server must be running.

2.  **From the `api-gateway` module directory, you can run the service using:**
    *   Maven:
        ```bash
        mvn spring-boot:run
        ```
    *   Executable JAR (after packaging):
        ```bash
        java -jar target/api-gateway-1.0-SNAPSHOT.jar
        ```
        (The JAR file is created in the `api-gateway/target` directory after running `mvn package` or `mvn install` in the `api-gateway` directory or the root project directory).

## Example Endpoints (Testing)

Once the API Gateway and the respective downstream services (`backend-service-alpha`, `cdn-simulation`) are running, you can test the routing:

*   **Access `backend-service-alpha`:**
    ```bash
    curl http://localhost:8080/alpha/api/greet
    ```
    Expected output: `Hello from Backend Service Alpha!`

*   **Access `cdn-simulation`:**
    ```bash
    curl http://localhost:8080/cdn/sample.txt
    ```
    Expected output: The content of `sample.txt` (e.g., "This is a sample text file for the CDN simulation...").

*   **Rate Limiting Test:**
    If you send too many requests in a short period (by default, more than 10 requests within 60 seconds from the same IP address), you will receive an HTTP `429 Too Many Requests` error. You can test this by rapidly executing one of the `curl` commands above multiple times.
