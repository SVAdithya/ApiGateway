package com.example.cdnsimulation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CdnSimulationApplication {

    public static void main(String[] args) {
        SpringApplication.run(CdnSimulationApplication.class, args);
    }

}
