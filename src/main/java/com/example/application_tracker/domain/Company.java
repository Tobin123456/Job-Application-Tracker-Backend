package com.example.application_tracker.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comp_seq_generator")
    @SequenceGenerator(
            name = "comp_seq_generator",
            sequenceName = "company_seq",
            allocationSize = 50
    )
    private Long id;

    @Column(nullable = false)
    private String name;

    // Hibernate requires a no-arg constructor
    protected Company() {
    }

    public Company(String name) {
        this.name = Objects.requireNonNull(name, "name cannot be null");
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

