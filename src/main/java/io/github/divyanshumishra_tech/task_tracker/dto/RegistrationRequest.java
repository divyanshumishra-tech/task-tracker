package io.github.divyanshumishra_tech.task_tracker.dto;

import lombok.Data;

@Data
public class RegistrationRequest {
    private String companyName;
    private String subdomain;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}