# Microservices Project with Spring Boot

## Overview

This project demonstrates various microservice patterns and technologies using Spring Boot and related Spring projects. The goal is to provide a practical example of an API Gateway, rate limiting, different Single Sign-On (SSO) approaches, a Content Delivery Network (CDN) simulation, and backend services.

## Modules

The project is structured into the following Maven modules:

*   **`my-app` (Root POM)**
    *   **Purpose:** This is the parent POM file that manages the overall project structure, common dependencies, and build configuration for all sub-modules.
    *   **ArtifactId:** `my-app`
    *   **Packaging:** `pom`

*   **`api-gateway`**
    *   **Purpose:** Implements an API Gateway using Spring Cloud Gateway. It acts as a single entry point for client requests, routing them to appropriate backend services. It also integrates the rate limiting functionality and serves as a reverse proxy.
    *   **ArtifactId:** `api-gateway`
    *   **Packaging:** `jar`

*   **`rate-limiter`**
    *   **Purpose:** Provides rate limiting logic using Redis as a backend to store request counts. This service is consumed by the `api-gateway` to protect downstream services from being overwhelmed.
    *   **ArtifactId:** `rate-limiter`
    *   **Packaging:** `jar`

*   **`sso-kerberos`** (Stub Implementation)
    *   **Purpose:** Placeholder module intended for implementing Single Sign-On (SSO) using Kerberos. This demonstrates how a Kerberos-based authentication mechanism could be integrated.
    *   **ArtifactId:** `sso-kerberos`
    *   **Packaging:** `jar`

*   **`sso-oauth2-oidc`** (Stub Implementation)
    *   **Purpose:** Placeholder module intended for implementing Single Sign-On (SSO) using OAuth 2.0 and OpenID Connect (OIDC) with Spring Authorization Server.
    *   **ArtifactId:** `sso-oauth2-oidc`
    *   **Packaging:** `jar`

*   **`cdn-simulation`**
    *   **Purpose:** Simulates a Content Delivery Network (CDN) by serving static assets (e.g., text files, images) and caching them using Spring's caching abstraction with Caffeine as the cache provider.
    *   **ArtifactId:** `cdn-simulation`
    *   **Packaging:** `jar`

*   **`backend-service-alpha`**
    *   **Purpose:** A sample backend microservice that exposes simple RESTful APIs. This service is used to demonstrate routing and upstream communication from the API Gateway.
    *   **ArtifactId:** `backend-service-alpha`
    *   **Packaging:** `jar`

## Prerequisites

To build and run this project, you will need:

*   **Java JDK:** Version 17 or later.
*   **Maven:** Version 3.8 or later.
*   **Redis:** Required for the `rate-limiter` service. Ensure Redis server is running on `localhost:6379` (default configuration).
*   **Kerberos KDC:** For the `sso-kerberos` module (when fully implemented), a Kerberos Key Distribution Center (KDC) setup would be required.

## Build Instructions

To build the entire project and all its modules, navigate to the root directory (`my-app`) and run:

```bash
mvn clean install
```
This command will compile the code, run any tests, and package each module into its respective `target` directory.

## Running the Services

It's recommended to run each service in its own terminal window. You can run them using the Spring Boot Maven plugin or by executing the packaged JAR file.

*   **API Gateway (`api-gateway`)**
    *   Port: `8080`
    *   Command:
        ```bash
        cd api-gateway
        mvn spring-boot:run
        ```
        or
        ```bash
        java -jar api-gateway/target/api-gateway-1.0-SNAPSHOT.jar
        ```

*   **Rate Limiter (`rate-limiter`)**
    *   This service provides logic used by the API Gateway. It doesn't run as a standalone server process itself but requires Redis.
    *   **Ensure Redis is running:** Typically on `localhost:6379`.

*   **SSO Kerberos (`sso-kerberos`)** (Stub)
    *   Port: `8082`
    *   Command:
        ```bash
        cd sso-kerberos
        mvn spring-boot:run
        ```
        or
        ```bash
        java -jar sso-kerberos/target/sso-kerberos-1.0-SNAPSHOT.jar
        ```
    *   Note: This is currently a stub and does not have full Kerberos functionality.

*   **SSO OAuth2/OIDC (`sso-oauth2-oidc`)** (Stub)
    *   Port: `8083`
    *   Command:
        ```bash
        cd sso-oauth2-oidc
        mvn spring-boot:run
        ```
        or
        ```bash
        java -jar sso-oauth2-oidc/target/sso-oauth2-oidc-1.0-SNAPSHOT.jar
        ```
    *   Note: This is currently a stub and does not have full OAuth2/OIDC functionality.

*   **CDN Simulation (`cdn-simulation`)**
    *   Port: `8084`
    *   Command:
        ```bash
        cd cdn-simulation
        mvn spring-boot:run
        ```
        or
        ```bash
        java -jar cdn-simulation/target/cdn-simulation-1.0-SNAPSHOT.jar
        ```

*   **Backend Service Alpha (`backend-service-alpha`)**
    *   Port: `9001`
    *   Command:
        ```bash
        cd backend-service-alpha
        mvn spring-boot:run
        ```
        or
        ```bash
        java -jar backend-service-alpha/target/backend-service-alpha-1.0-SNAPSHOT.jar
        ```

## Accessing Endpoints (Examples)

Once the necessary services are running (API Gateway, Backend Service Alpha, CDN Simulation, and Redis), you can access the following endpoints through the API Gateway:

*   **Access Backend Service Alpha via Gateway:**
    *   URL: `http://localhost:8080/alpha/api/greet`
    *   This request will be routed by the `api-gateway` to the `backend-service-alpha`.

*   **Access CDN Content via Gateway:**
    *   URL: `http://localhost:8080/cdn/sample.txt`
    *   This request will be routed by the `api-gateway` to the `cdn-simulation` service, which will serve the `sample.txt` file.

**Note on Rate Limiting:** The rate limiting functionality (10 requests per 60 seconds per IP by default) is active on all routes handled by the API Gateway. If you exceed this limit, you will receive an HTTP `429 Too Many Requests` error.