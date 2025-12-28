package com.example.application_tracker.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class ResponseApplicationDto {


    private final String jobTitle;

    private final String companyName;

    private final String url;

    public ResponseApplicationDto(String jobTitle, String companyName, String url) {
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.url = url;
    }

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
