package com.example.backendservicealpha;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/greet")
    public String greet() {
        return "Hello from Backend Service Alpha!";
    }
}
