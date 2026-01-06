package com.example.application_tracker.repository;

import com.example.application_tracker.domain.Application;
import com.example.application_tracker.domain.Company;
import com.example.application_tracker.domain.Job;
import com.example.application_tracker.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.HashSet;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ApplicationTrackerRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;


    @Test
    void testCreateApplicationHierarchy() {
        // Create and save entities
        Company company = companyRepository.save(new Company("MyCompany"));
        Job job = jobRepository.save(new Job("Java Developer", company, "https://example.com/job/1"));
        Application application = applicationRepository.save(new Application(job));

        // Read back from DB
        Company dbCompany = companyRepository.findById(company.getId()).orElseThrow();
        Job dbJob = jobRepository.findById(job.getId()).orElseThrow();
        Application dbApplication = applicationRepository.findById(application.getId()).orElseThrow();

        // Assertions
        Assertions.assertEquals(company.getName(), dbCompany.getName());
        Assertions.assertEquals(job.getTitle(), dbJob.getTitle());
        Assertions.assertEquals(company.getId(), dbJob.getCompany().getId());
        Assertions.assertEquals(job.getId(), dbApplication.getJob().getId());

        // Check counts in DB
        Assertions.assertEquals(1, companyRepository.count());
        Assertions.assertEquals(1, jobRepository.count());
        Assertions.assertEquals(1, applicationRepository.count());
    }

    @Test
    void testCreateUserHierarchy() {
        // Create and save entities
        User user = userRepository.save(new User("FactorioEnjoyer", "theFactoryMustGrow", new HashSet<String>(List.of("Engineer"))));

        // Read back from DB
        User userDbWithID = userRepository.findById(user.getId()).orElseThrow();
        User userDbWithName = userRepository.findById(user.getId()).orElseThrow();

        // Assertions
        Assertions.assertEquals(user.getUsername(), userDbWithID.getUsername());
        Assertions.assertEquals(user.getPassword(), userDbWithID.getPassword());
        Assertions.assertEquals(user.getRoles(), userDbWithID.getRoles());
        Assertions.assertEquals(user.getUsername(), userDbWithName.getUsername());
        Assertions.assertEquals(user.getPassword(), userDbWithName.getPassword());
        Assertions.assertEquals(user.getRoles(), userDbWithName.getRoles());

        // Check counts in DB
        Assertions.assertEquals(1, userRepository.count());
    }
}
