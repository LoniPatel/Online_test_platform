package com.example;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "APIs",version = "1.0",description = "Test management"))
public class OnlineTestPlatformApplication {
    public static void main(String[] args) {
        SpringApplication.run(OnlineTestPlatformApplication.class, args);
    }

}