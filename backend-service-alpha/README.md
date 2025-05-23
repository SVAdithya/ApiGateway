# Example Backend Service - Alpha

## Purpose

This service, `backend-service-alpha`, serves as a sample downstream microservice within the larger application ecosystem. Its primary roles are:

*   To act as a concrete example of a backend service that other services (like the `api-gateway`) can interact with.
*   To demonstrate the routing capabilities of the `api-gateway`. Requests sent to a specific path on the gateway are forwarded to this service.
*   To provide a simple, predictable RESTful endpoint for testing inter-service communication and request handling.

## Technologies Used

*   **Spring Boot:** Provides the core application framework, enabling rapid development and deployment.
*   **Spring Web:** Used for creating RESTful web services, including the `HelloController`.

## Key Features

*   **`HelloController.java`:**
    *   This is the main controller in the service.
    *   It defines a single GET endpoint mapped to the path `/api/greet`.
    *   When this endpoint is accessed, it returns a static string message: `"Hello from Backend Service Alpha!"`.

## Configuration (from `application.properties`)

The service is configured with the following property in `backend-service-alpha/src/main/resources/application.properties`:

*   **Server Port:**
    *   The service runs on port `9001`.
    ```properties
    server.port=9001
    ```

## How to Run

1.  **Prerequisites:**
    *   The root project (`my-app`) should ideally be built first (`mvn clean install` in the root directory) to ensure all dependencies are available.

2.  **From the `backend-service-alpha` module directory, you can run the service using:**
    *   Maven:
        ```bash
        mvn spring-boot:run
        ```
    *   Executable JAR (after packaging):
        ```bash
        java -jar target/backend-service-alpha-1.0-SNAPSHOT.jar
        ```
        (The JAR file is created in the `backend-service-alpha/target` directory after running `mvn package` or `mvn install` in the `backend-service-alpha` directory or the root project directory).

## How to Access Endpoint

You can access the REST endpoint provided by this service in the following ways:

*   **Directly:**
    *   URL: `http://localhost:9001/api/greet`
    *   This directly calls the `backend-service-alpha`.

*   **Via API Gateway:**
    *   URL: `http://localhost:8080/alpha/api/greet`
    *   This assumes the `api-gateway` service is running on port `8080` and has been configured with a route (e.g., `/alpha/**`) that forwards requests to this `backend-service-alpha` at `http://localhost:9001`.

In both cases, the expected response is the string: `"Hello from Backend Service Alpha!"`.
