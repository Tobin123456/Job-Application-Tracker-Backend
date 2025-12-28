package com.example.application_tracker.controller;

import com.example.application_tracker.domain.Application;
import com.example.application_tracker.domain.Company;
import com.example.application_tracker.domain.Job;
import com.example.application_tracker.dto.CreateApplicationDto;
import com.example.application_tracker.dto.ResponseApplicationDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    private final List<Application> applications = new ArrayList<>();

    @GetMapping
    public List<Application> getAll() {
        return applications;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseApplicationDto create(@Valid @RequestBody CreateApplicationDto dto) {
        Company company = new Company(dto.getCompanyName());
        Job job = new Job(dto.getJobTitle(), company, dto.getUrl());
        Application application = new Application(job);
        applications.add(application);

        return new ResponseApplicationDto(
                job.getTitle(),
                company.getName(),
                application.getStatus().name()
        );
    }
}
