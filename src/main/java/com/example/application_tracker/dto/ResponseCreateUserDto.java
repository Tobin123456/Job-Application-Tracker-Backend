package com.example.application_tracker.dto;

import jakarta.validation.constraints.NotBlank;

public class ResponseCreateUserDto {

    @NotBlank
    private String message;

    public ResponseCreateUserDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
