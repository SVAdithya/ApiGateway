# SSO Service - Kerberos

## Purpose

This module is intended to provide Single Sign-On (SSO) functionality using the Kerberos network authentication protocol. The goal is to enable users to authenticate once with a Kerberos Key Distribution Center (KDC) and then access this service (and potentially others) without needing to re-enter credentials.

It is planned to integrate with the Spring Security Kerberos extension to achieve this.

## Current Status

**This module is currently a stub/placeholder.**

*   The basic project structure for a Spring Boot application is in place.
*   Essential dependencies, such as `spring-boot-starter-web`, `spring-boot-starter-security`, and `spring-security-kerberos-web`, have been included in the `pom.xml`.
*   A basic `SsoKerberosApplication.java` class and an `application.properties` file exist.
*   However, the actual Kerberos authentication logic, security configuration (e.g., `WebSecurityConfigurer`), and integration with a Kerberos KDC are **not yet implemented**.

## Technologies Planned

The full implementation will leverage the following technologies:

*   **Spring Boot:** For the core application framework.
*   **Spring Security:** To handle authentication and authorization.
*   **Spring Security Kerberos:** The extension specifically designed to integrate Kerberos authentication with Spring Security.

## Future Configuration (Placeholder - to be detailed when implemented)

When fully implemented, the following configuration items will be crucial (likely in `application.properties` or a dedicated Kerberos configuration file):

*   **Service Principal Name (SPN):** The unique identifier for the service in the Kerberos realm (e.g., `HTTP/myservice.example.com@EXAMPLE.COM`).
    *   Example property: `app.service-principal=...`
*   **Keytab File Location:** The path to the file containing the service's secret key (e.g., `/etc/krb5.keytab` or a path within the application).
    *   Example property: `app.keytab-location=...`
*   **Kerberos Configuration File (`krb5.conf`):** If not located in a default system path (e.g., `/etc/krb5.conf`), its location might need to be specified.
*   **Spring Security Configuration:**
    *   An `AuthenticationProvider` bean (e.g., `KerberosServiceAuthenticationProvider` for service ticket validation or `KerberosLdapAuthenticationProvider` if integrating with LDAP for user details) will need to be configured.
    *   `WebSecurityConfigurerAdapter` (or its modern equivalent using `SecurityFilterChain` beans) will be set up to protect endpoints and trigger Kerberos authentication.

## How to Run (Current State)

1.  **Prerequisites:**
    *   The root project (`my-app`) should ideally be built first (`mvn clean install` in the root directory) to ensure all dependencies are available.

2.  **From the `sso-kerberos` module directory, you can run the service using:**
    *   Maven:
        ```bash
        mvn spring-boot:run
        ```
    *   Executable JAR (after packaging):
        ```bash
        java -jar target/sso-kerberos-1.0-SNAPSHOT.jar
        ```
        (The JAR file is created in the `sso-kerberos/target` directory after running `mvn package` or `mvn install` in the `sso-kerberos` directory or the root project directory).

*   The service starts and runs on port `8082` by default (as configured in `application.properties`).
*   **Important Note:** In its current stub state, the service will start, but it **does not perform any actual Kerberos authentication** or protect any resources. Accessing it will not trigger any SSO flow. The basic Spring Boot application will run, but the Kerberos-specific security features are not yet active.
