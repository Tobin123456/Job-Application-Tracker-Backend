package com.example.application_tracker.controller;

import com.example.application_tracker.dto.HealthDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/health")
public class HealthController {

    @GetMapping
    public HealthDto health() {
        return new HealthDto("Up");
    }
}

