package com.example.application_tracker.config;

import com.example.application_tracker.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // Constructor injection of dependencies (filter and user details service)
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, UserDetailsService userDetailsService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        // Spring auto-configure the AuthenticationManager with UserDetailsService and PasswordEncoder
        return authConfig.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // REST API with Bearer Token→ no CSRF necessary
                .csrf(AbstractHttpConfigurer::disable)

                // Stateless API via Bearer Token  → no server  side HTTP sessions necessary
                // all state data is encoded into the token itself
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                org.springframework.security.config.http.SessionCreationPolicy.STATELESS
                        )
                )

                // Authorization rules, via request matchers: pattern -> behavior pars in order
                // A request has the behavior of the first request matchers it matches with
                .authorizeHttpRequests(auth -> auth
                        // allow Swagger
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**", "/api/health", "/api/user"
                        ).permitAll()

                        // everything else requires authentication
                        .anyRequest().authenticated()
                )
                // Enable http basic authentication
                .httpBasic(Customizer.withDefaults())
                // Disable default login form &
                .formLogin(AbstractHttpConfigurer::disable)
                // Add Bearer Token Filter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
