package com.example.application_tracker.dto;

import java.util.Set;

public class CreateUserDto {

    private String username;

    private String password;

    private Set<String> roles;  // roles as a set of strings (e.g., ["ROLE_USER"])

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Set<String> getRoles() {
        return roles;
    }

}
