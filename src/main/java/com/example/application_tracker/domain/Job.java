package com.example.application_tracker.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "job_seq_generator")
    @SequenceGenerator(
            name = "job_seq_generator",
            sequenceName = "job_seq",
            allocationSize = 50
    )
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(nullable = true)
    private String url;

    // Hibernate requires a no-arg constructor
    protected Job() {
    }

    // Constructor for application use
    public Job(String title, Company company, String url) {
        this.title = Objects.requireNonNull(title, "title cannot be null");
        this.company = Objects.requireNonNull(company, "company cannot be null");
        this.url = url;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Company getCompany() {
        return company;
    }

    public String getUrl() {
        return url;
    }
}

