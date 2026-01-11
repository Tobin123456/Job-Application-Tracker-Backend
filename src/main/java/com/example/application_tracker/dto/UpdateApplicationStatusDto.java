package com.example.application_tracker.dto;

import com.example.application_tracker.domain.Application;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record UpdateApplicationStatusDto(@NotNull Application.Status status) {

    public UpdateApplicationStatusDto(@JsonProperty("status") Application.Status status) {
        this.status = status;
    }
}
