package com.example.application_tracker.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class CreateApplicationDto {
    @NotBlank
    private String jobTitle;

    @NotBlank
    private String companyName;

    @URL
    private String url;

    public String getUrl() {
        return url;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getJobTitle() {
        return jobTitle;
    }
}
