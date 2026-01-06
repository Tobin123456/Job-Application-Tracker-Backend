package com.example.application_tracker.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

/**
 * @param roles roles as a set of strings (e.g., ["ROLE_USER"])
 */
public record CreateUserDto(String username, String password, Set<String> roles) {

    @JsonCreator
    public CreateUserDto(
            @JsonProperty("username") String username,
            @JsonProperty("password") String password,
            @JsonProperty("roles") Set<String> roles
    ) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}
