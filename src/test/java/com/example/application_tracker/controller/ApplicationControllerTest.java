package com.example.application_tracker.controller;


import com.example.application_tracker.domain.Application;
import com.example.application_tracker.dto.CreateApplicationDto;
import com.example.application_tracker.dto.ResponseApplicationDto;
import com.example.application_tracker.dto.UpdateApplicationStatusDto;
import com.example.application_tracker.security.JwtUtils;
import com.example.application_tracker.service.ApplicationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApplicationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ApplicationService applicationService;

    @MockitoBean
    private JwtUtils jwtUtils;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Long applicationID = 1234567L;

    @AfterEach
    void tearDown() {
        reset(applicationService, jwtUtils);
    }

    @Test
    void shouldReturnAllApplications() throws Exception {
        // given
        ResponseApplicationDto responseDto = new ResponseApplicationDto(
                applicationID,
                "Backend Developer",
                "ACME",
                Application.Status.APPLIED,
                "https://jobs.acme.com/backend"
        );
        List<ResponseApplicationDto> applications = List.of(responseDto);

        when(applicationService.getAllApplications()).thenReturn(applications);

        // when + then
        mockMvc.perform(get("/api/applications")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].appID").value(responseDto.appID()))
                .andExpect(jsonPath("$[0].jobTitle").value(responseDto.jobTitle()))
                .andExpect(jsonPath("$[0].companyName").value(responseDto.companyName()))
                .andExpect(jsonPath("$[0].status").value(responseDto.status().name()))
                .andExpect(jsonPath("$[0].jobUrl").value(responseDto.jobUrl()));
    }

    @Test
    void shouldCreateApplication() throws Exception {
        // given
        CreateApplicationDto requestDto = new CreateApplicationDto(
                "Backend Developer",
                "ACME",
                "https://jobs.acme.com/backend"
        );

        ResponseApplicationDto responseDto = new ResponseApplicationDto(
                applicationID,
                "Backend Developer",
                "ACME",
                Application.Status.APPLIED,
                "https://jobs.acme.com/backend"
        );

        when(applicationService.createApplication(any(CreateApplicationDto.class)))
                .thenReturn(responseDto);

        // when + then
        mockMvc.perform(post("/api/applications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.appID").value(responseDto.appID()))
                .andExpect(jsonPath("$.jobTitle").value(responseDto.jobTitle()))
                .andExpect(jsonPath("$.companyName").value(responseDto.companyName()))
                .andExpect(jsonPath("$.status").value(responseDto.status().name()))
                .andExpect(jsonPath("$.jobUrl").value(responseDto.jobUrl()));
    }

    @Test
    void shouldUpdateStatus() throws Exception {
        // given
        UpdateApplicationStatusDto requestDto = new UpdateApplicationStatusDto(
                Application.Status.INTERVIEW
        );

        doNothing().when(applicationService).updateApplicationStatus(any(Long.class), any(UpdateApplicationStatusDto.class));

        // when + then
        mockMvc.perform(patch("/api/applications/1234567/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isNoContent());
    }
}
