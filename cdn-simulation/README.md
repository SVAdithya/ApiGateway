# CDN Simulation Service

## Purpose

This service simulates a basic Content Delivery Network (CDN) by:

*   Serving static assets (e.g., text files, images, stylesheets).
*   Implementing in-memory caching for these assets to improve performance for repeated requests and reduce the effective load on the origin of the content (though in this simple setup, it's serving its own static content from the classpath).

The aim is to demonstrate how Spring's caching mechanism can be used to build a simple content cache.

## Technologies Used

*   **Spring Boot:** For the core application framework and auto-configuration.
*   **Spring Cache:** Provides the caching abstraction layer. The `@EnableCaching` annotation is used to activate caching.
*   **Caffeine:** A high-performance, near-optimal caching library used as the concrete caching provider.

## Key Features & Configuration

*   **Static Content Serving:**
    *   Serves static content primarily using Spring Boot's default static resource handling, which looks for files in `src/main/resources/static/`.
    *   An example file, `sample.txt`, is included in `src/main/resources/static/sample.txt`.
*   **Caching:**
    *   Caching is enabled on the application level via `@EnableCaching` in `CdnSimulationApplication.java`.
    *   The cache behavior is configured in `cdn-simulation/src/main/resources/application.properties`:
        ```properties
        spring.cache.type=caffeine
        spring.cache.caffeine.spec=maximumSize=100,expireAfterAccess=600s
        ```
        *   `spring.cache.type=caffeine`: Specifies Caffeine as the caching provider.
        *   `spring.cache.caffeine.spec`: Configures Caffeine. In this example, the cache can hold up to 100 entries, and entries expire 600 seconds (10 minutes) after the last access.
*   **`StaticContentController.java`:**
    *   A simple `StaticContentController` is provided. While Spring Boot's auto-configuration for static resources handles serving from the `/static` classpath location directly (e.g., accessing `/sample.txt`), the controller includes an explicit mapping for paths like `/static/{filename:.+}`.
    *   The controller's `serveStatic` method currently forwards the request to be handled by Spring Boot's static resource resolver (e.g., `return "forward:/" + filename;`). For this basic setup, the controller is more of a placeholder for potential future enhancements like custom header manipulation or more complex content resolution logic, rather than being strictly necessary for serving simple static files from `/static`.

## How to Run

1.  **Prerequisites:**
    *   The root project (`my-app`) should ideally be built first (`mvn clean install` in the root directory) to ensure all dependencies are available.

2.  **From the `cdn-simulation` module directory, you can run the service using:**
    *   Maven:
        ```bash
        mvn spring-boot:run
        ```
    *   Executable JAR (after packaging):
        ```bash
        java -jar target/cdn-simulation-1.0-SNAPSHOT.jar
        ```
        (The JAR file is created in the `cdn-simulation/target` directory after running `mvn package` or `mvn install` in the `cdn-simulation` directory or the root project directory).

*   The service runs on port `8084` by default (as configured in `application.properties`).

## How to Access Content

You can access the static content served by this module in the following ways:

*   **Directly:**
    *   URL: `http://localhost:8084/sample.txt`
    *   This accesses the `sample.txt` file directly from the `cdn-simulation` service.

*   **Via API Gateway:**
    *   URL: `http://localhost:8080/cdn/sample.txt`
    *   This assumes the `api-gateway` service is running and configured with a route like `/cdn/**` pointing to `http://localhost:8084`.

**Caching Behavior:**
When a file like `sample.txt` is requested for the first time, it will be loaded from the source (`src/main/resources/static`) and served. This request will also populate the cache. Subsequent requests for the same asset (within the 600-second `expireAfterAccess` window) should be served directly from the Caffeine cache, resulting in faster response times.
While `curl` or browser access will show the content, observing the caching effect typically requires checking application logs (if cache logging is enabled) or using monitoring tools to inspect cache hits/misses. For Spring Boot, HTTP caching headers (like `Cache-Control`, `ETag`) would also play a role in how clients and intermediate proxies cache the content, which can be further configured.
