package com.example.application_tracker.service;

import com.example.application_tracker.domain.Application;
import com.example.application_tracker.domain.Company;
import com.example.application_tracker.domain.Job;
import com.example.application_tracker.dto.CreateApplicationDto;
import com.example.application_tracker.dto.ResponseApplicationDto;
import com.example.application_tracker.dto.UpdateApplicationStatusDto;
import com.example.application_tracker.mapper.ApplicationMapper;
import com.example.application_tracker.repository.ApplicationRepository;
import com.example.application_tracker.repository.CompanyRepository;
import com.example.application_tracker.repository.JobRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class ApplicationServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private ApplicationRepository applicationRepository;

    // Service under test
    private ApplicationService applicationService;

    // Shared test data
    private Company acmeCompany;
    private Job backendJob;
    private Application backendApplication;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
        // Real mapper
        ApplicationMapper applicationMapper = Mappers.getMapper(ApplicationMapper.class);

        // Create the service with mocks + real mapper
        applicationService = new ApplicationService(
                companyRepository,
                jobRepository,
                applicationRepository,
                applicationMapper
        );

        // Centralize shared test entities
        acmeCompany = new Company("ACME");
        backendJob = new Job("Backend Developer", acmeCompany, "https://jobs.acme.com/backend");
        backendApplication = new Application(backendJob);
    }

    @Test
    void shouldGetAllApplications() {
        // given
        when(applicationRepository.findAll()).thenReturn(List.of(backendApplication));

        // when
        List<ResponseApplicationDto> result = applicationService.getAllApplications();

        // then
        assertThat(result).hasSize(1);
        ResponseApplicationDto dto = result.getFirst();
        assertThat(dto.jobTitle()).isEqualTo(backendJob.getTitle());
        assertThat(dto.companyName()).isEqualTo(acmeCompany.getName());
        assertThat(dto.jobUrl()).isEqualTo(backendJob.getUrl());
        assertThat(dto.status()).isEqualTo(Application.Status.APPLIED);
    }

    @Test
    void shouldCreateApplication() {
        // given
        CreateApplicationDto dto = new CreateApplicationDto(
                backendJob.getTitle(),
                acmeCompany.getName(),
                backendJob.getUrl()
        );

        // mock saves to just return the entity (simulating repository behavior)
        when(companyRepository.save(any())).thenReturn(acmeCompany);
        when(jobRepository.save(any())).thenReturn(backendJob);
        when(applicationRepository.save(any())).thenReturn(backendApplication);

        // when
        ResponseApplicationDto result = applicationService.createApplication(dto);

        // then
        assertThat(result.jobTitle()).isEqualTo(backendJob.getTitle());
        assertThat(result.companyName()).isEqualTo(acmeCompany.getName());
        assertThat(result.jobUrl()).isEqualTo(backendJob.getUrl());
        assertThat(result.status()).isEqualTo(Application.Status.APPLIED);

        verify(companyRepository).save(any());
        verify(jobRepository).save(any());
        verify(applicationRepository).save(any());
    }

    @Test
    void shouldUpdateApplicationStatus() {
        // given
        UpdateApplicationStatusDto statusDto = new UpdateApplicationStatusDto(
                backendApplication.getId(),
                Application.Status.INTERVIEW
        );

        when(applicationRepository.findById(backendApplication.getId()))
                .thenReturn(java.util.Optional.of(backendApplication));

        // when
        applicationService.updateApplicationStatus(statusDto);

        // then
        assertThat(backendApplication.getStatus()).isEqualTo(Application.Status.INTERVIEW);
    }

    @Test
    void updateStatusShouldThrowWhenApplicationNotFound() {
        // given
        UpdateApplicationStatusDto statusDto = new UpdateApplicationStatusDto(
                999L,
                Application.Status.INTERVIEW
        );

        when(applicationRepository.findById(999L))
                .thenReturn(java.util.Optional.empty());

        // then
        assertThatThrownBy(() -> applicationService.updateApplicationStatus(statusDto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Application with ID 999 not found");
    }
}
