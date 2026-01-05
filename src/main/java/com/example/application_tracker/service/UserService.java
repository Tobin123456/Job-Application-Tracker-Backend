package com.example.application_tracker.service;

import com.example.application_tracker.domain.User;
import com.example.application_tracker.dto.CreateUserDto;
import com.example.application_tracker.dto.ResponseCreateUserDto;
import com.example.application_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Transactional
    public ResponseCreateUserDto createApplication(CreateUserDto createUserDto) {
        if (userRepository.existsByUsername(createUserDto.getUsername())) {
            return new ResponseCreateUserDto("Username already taken!");
        }
        User user = new User(createUserDto.getUsername(), passwordEncoder.encode(createUserDto.getPassword()), createUserDto.getRoles());
        userRepository.save(user);
        return new ResponseCreateUserDto("User created successfully");
    }
}


