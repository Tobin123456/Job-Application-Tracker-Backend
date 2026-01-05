package com.example.application_tracker.dto;

import jakarta.validation.constraints.NotBlank;

public class ResponseLoginDto {

    @NotBlank
    private String token;

    public ResponseLoginDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

