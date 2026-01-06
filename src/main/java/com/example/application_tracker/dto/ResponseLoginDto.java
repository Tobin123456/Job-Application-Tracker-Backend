package com.example.application_tracker.dto;

import jakarta.validation.constraints.NotBlank;

public record ResponseLoginDto(@NotBlank String token) {

    public ResponseLoginDto(String token) {
        this.token = token;
    }
}

