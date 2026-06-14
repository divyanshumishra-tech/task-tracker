package io.github.divyanshumishra_tech.task_tracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Tells Spring Boot "Read this file before starting the server!"
@EnableWebSecurity // Takes control away from the aggressive default security guard
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Turn off CSRF protection because we are building an API, not a traditional website
            .csrf(csrf -> csrf.disable()) 
            
            // 2. Set the rules for who is allowed inside
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll() // Leave the door WIDE OPEN for registration and login
                .anyRequest().authenticated() // Lock down every other route in the future
            );
        
        return http.build();
    }
}