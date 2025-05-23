# SSO Service - OAuth2/OIDC Authorization Server

## Purpose

This module is intended to function as an OAuth 2.0 Authorization Server and OpenID Connect (OIDC) Provider. It will be responsible for:

*   Authenticating resource owners and issuing consents.
*   Issuing tokens (access tokens, refresh tokens, and OIDC ID tokens) to client applications.
*   Managing registered client applications and their permitted scopes and grant types.
*   Securing and exposing standard OAuth 2.0 and OIDC endpoints (e.g., authorization, token, user info, JWK Set).
*   This will be achieved by leveraging the **Spring Authorization Server** project.

## Current Status

**This module is currently a stub/placeholder.**

*   The basic project structure for a Spring Boot application is in place.
*   Essential dependencies, particularly `spring-boot-starter-oauth2-authorization-server`, `spring-boot-starter-web`, and `spring-boot-starter-security`, have been included in the `pom.xml`.
*   A basic `SsoOAuth2OIDCApplication.java` class and an `application.properties` file exist.
*   However, the actual OAuth 2.0 and OIDC server configuration, including client registration, key management for token signing, and detailed endpoint setup, is **not yet implemented**.

## Technologies Planned

The full implementation will leverage the following technologies:

*   **Spring Boot:** For the core application framework.
*   **Spring Security:** To handle underlying authentication mechanisms and secure the authorization server itself.
*   **Spring Authorization Server:** The core library for providing OAuth 2.0 and OIDC capabilities.

## Future Configuration (Placeholder - to be detailed when implemented)

When fully implemented, the following configuration aspects will be crucial (primarily within Spring Security configuration classes and potentially `application.properties`):

*   **`RegisteredClientRepository`:**
    *   Configuration of one or more `RegisteredClient` instances. This includes:
        *   Client ID and Client Secret (for confidential clients).
        *   Client authentication methods (e.g., `client_secret_basic`).
        *   Allowed redirect URIs.
        *   Permitted scopes (e.g., `openid`, `profile`, `email`, custom scopes).
        *   Authorization grant types (e.g., `authorization_code`, `refresh_token`, `client_credentials`).
*   **JWK Set (`JWKSource`):**
    *   Configuration for generating and exposing JSON Web Keys (JWKs) used for signing ID tokens and potentially access tokens. This involves setting up an RSA key pair.
    *   The JWK Set endpoint (`/oauth2/jwks` by default) will publish the public keys.
*   **Issuer URI (`ProviderSettings`):**
    *   The unique identifier for this authorization server (e.g., `http://localhost:8083`). This is crucial for OIDC discovery and token validation.
    *   Example property: `spring.security.oauth2.authorizationserver.provider.issuer-uri=...`
*   **OAuth 2.0 / OIDC Endpoints:**
    *   The Spring Authorization Server will automatically configure default endpoints:
        *   Authorization Endpoint (`/oauth2/authorize`)
        *   Token Endpoint (`/oauth2/token`)
        *   JWK Set URI (`/oauth2/jwks`)
        *   OIDC Provider Configuration (`/.well-known/openid-configuration`)
        *   User Info Endpoint (`/userinfo` - if OIDC scopes like `profile` are supported and a `OidcUserInfoService` is configured).
*   **User Authentication:**
    *   Integration with a user authentication mechanism (e.g., a form login, or potentially deferring to another SSO system) to authenticate the resource owner before issuing tokens.

## How to Run (Current State)

1.  **Prerequisites:**
    *   The root project (`my-app`) should ideally be built first (`mvn clean install` in the root directory) to ensure all dependencies are available.

2.  **From the `sso-oauth2-oidc` module directory, you can run the service using:**
    *   Maven:
        ```bash
        mvn spring-boot:run
        ```
    *   Executable JAR (after packaging):
        ```bash
        java -jar target/sso-oauth2-oidc-1.0-SNAPSHOT.jar
        ```
        (The JAR file is created in the `sso-oauth2-oidc/target` directory after running `mvn package` or `mvn install` in the `sso-oauth2-oidc` directory or the root project directory).

*   The service starts and runs on port `8083` by default (as configured in `application.properties`).
*   **Important Note:** In its current stub state, while the service starts, it **does not function as a complete OAuth2/OIDC provider.** Key configurations for client registration, token signing, and user authentication are missing. Accessing OAuth2/OIDC endpoints will likely result in errors or default Spring Security behavior rather than a full SSO flow.
