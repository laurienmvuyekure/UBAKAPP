package com.ubakapp.ubakapp_backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "UbakApp Backend is running successfully!";
    }

    @GetMapping("/health")
    public String health() {
        return "{\"status\":\"UP\",\"database\":\"connected\",\"timestamp\":\"" + java.time.LocalDateTime.now() + "\"}";
    }
}