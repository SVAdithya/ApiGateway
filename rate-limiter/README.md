# Rate Limiter Service Logic

## Purpose

This module provides the core logic for IP-based request rate limiting. Its main functions are:

*   To determine if an incoming request from a specific IP address should be allowed based on configured request limits.
*   To use Redis as a distributed data store for tracking request counts and their expiration times for each unique IP address.
*   To be easily integrated as a dependency into other services, primarily the `api-gateway`, to protect downstream services from being overwhelmed by excessive requests.

## Technologies Used

*   **Spring Boot:** Provides the foundational framework for the components within this module.
*   **Spring Data Redis:** Used for seamless integration with Redis, which acts as the backend for storing rate limiting counters.
*   **Spring AOP:** While the current integration with the API Gateway is via direct service calls (injecting `RateLimitingService`), Spring AOP could be an alternative approach to apply rate limiting declaratively to methods or components in other modules.

## Key Components

*   **`RateLimitingService`:**
    *   This is the central class containing the rate limiting logic.
    *   The `isAllowed(String ipAddress)` method is the primary entry point. It checks the current request count for the given IP against the allowed limit within a defined time window. If the request is allowed, it increments the count in Redis and sets/updates the expiration for that IP's record.
*   **`RateLimiterConfig`:**
    *   Responsible for configuring the necessary Spring beans, specifically the `StringRedisTemplate`, which is used by `RateLimitingService` to interact with Redis.

## Configuration (from `rate-limiter/src/main/resources/application.properties`)

The following properties configure the rate limiter's behavior and Redis connection:

*   **Redis Connection:**
    *   `spring.data.redis.host`: Hostname of the Redis server (default: `localhost`).
    *   `spring.data.redis.port`: Port of the Redis server (default: `6379`).
    ```properties
    spring.data.redis.host=localhost
    spring.data.redis.port=6379
    ```

*   **Rate Limits:**
    *   `ratelimit.requests`: The maximum number of requests allowed from a single IP address within the defined window (default: `10`).
    *   `ratelimit.seconds`: The duration of the time window, in seconds, for which the request count is tracked (default: `60`).
    ```properties
    ratelimit.requests=10
    ratelimit.seconds=60
    ```

## Integration

*   This `rate-limiter` module is primarily designed to be used as a library or dependency by other Spring Boot applications.
*   In this project, it is a direct dependency of the `api-gateway` module.
*   The `RateLimitingService` bean (defined in this module) is auto-wired into the `RateLimitingFilter` (defined in the `api-gateway` module). The API Gateway then uses this service to perform rate checks for incoming requests.
*   The `ApiGatewayApplication` is configured with `@ComponentScan` to discover and create beans from the `com.example.ratelimiter` package.

## How to Use/Run

*   **This module is not intended to be run as a standalone service.** It does not contain a main application that starts an independent server process.
*   Its components (like `RateLimitingService`) are packaged as a JAR and included in the classpath of the consuming application (e.g., `api-gateway`).
*   **Prerequisites for Functionality (when used by `api-gateway`):**
    *   A Redis server must be running and accessible according to the configuration specified in *this* module's `application.properties` (or overridden by the consuming application).
    *   The consuming application (e.g., `api-gateway`) must have this module as a dependency and be configured to scan for its components.

When the `api-gateway` (or any other consuming service) starts, the `RateLimitingService` will be available for use, and it will connect to Redis as per its configuration to perform rate limiting operations.
