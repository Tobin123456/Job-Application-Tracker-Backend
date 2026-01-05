package com.example.application_tracker.controller;

import com.example.application_tracker.dto.HealthDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("api/health")
    public HealthDto health() {
        return new HealthDto("Up");
    }
}

