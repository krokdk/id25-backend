package com.id25.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.*;

@EnableCaching
@SpringBootApplication
public class Id25BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(Id25BackendApplication.class, args);
    }

}
