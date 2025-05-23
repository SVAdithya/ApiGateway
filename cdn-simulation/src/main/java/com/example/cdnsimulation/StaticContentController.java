package com.example.cdnsimulation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.HandlerMapping;

import jakarta.servlet.http.HttpServletRequest;

// Spring Boot's auto-configuration for static resources will serve files
// from src/main/resources/static by default.
// This controller is a placeholder if more specific logic or custom headers
// are needed in the future, or to explicitly handle paths.

@Controller
public class StaticContentController {

    // Example: Explicitly map requests to /static/** to resources.
    // This is often not needed if files are in src/main/resources/static/
    // and no special handling is required.
    @GetMapping("/static/{filename:.+}")
    public String serveStatic(@PathVariable String filename, HttpServletRequest request) {
        // The view resolver will attempt to find a resource in the static locations.
        // For example, a request to /static/sample.txt will look for sample.txt.
        // Spring Boot's default static resource handling often makes this explicit mapping unnecessary.
        // String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        // return path; // This would attempt to resolve the path as a view name.
        
        // Forward to the resource. Spring Boot's static resource handler will pick it up.
        // The resource should be directly accessible under / if placed in /static.
        // e.g. /sample.txt not /static/sample.txt if served directly by Spring Boot's default handler.
        // If we want to serve it under /static/ prefix, we might need to adjust WebMvcConfigurer.
        // For now, relying on default behavior.
        return "forward:/" + filename; // Forward to /sample.txt
    }
}
