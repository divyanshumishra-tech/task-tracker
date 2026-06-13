package io.github.divyanshumishra_tech.task_tracker.service;

import io.github.divyanshumishra_tech.task_tracker.entity.Organization;
import io.github.divyanshumishra_tech.task_tracker.entity.User;
import io.github.divyanshumishra_tech.task_tracker.repository.OrganizationRepository;
import io.github.divyanshumishra_tech.task_tracker.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service // Tells Spring Boot this is where our core business logic lives
public class UserService {

    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;

    // Spring Boot automatically injects the repositories for us to use
    public UserService(UserRepository userRepository, OrganizationRepository organizationRepository) {
        this.userRepository = userRepository;
        this.organizationRepository = organizationRepository;
    }

    // @Transactional means if ANY part of this fails, it rolls back the whole database save automatically
    @Transactional
    public User registerNewOrganizationAndAdmin(String companyName, String subdomain, String firstName, String lastName, String email, String rawPassword) {
        
        // 1. Rule Check: Is the email already in use?
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new RuntimeException("Email is already in use!");
        }

        // 2. Rule Check: Is the subdomain already taken?
        Optional<Organization> existingOrg = organizationRepository.findBySubdomain(subdomain);
        if (existingOrg.isPresent()) {
            throw new RuntimeException("Subdomain is already taken!");
        }

        // 3. Execution: Create and save the new Organization
        Organization newOrg = Organization.builder()
                .name(companyName)
                .subdomain(subdomain)
                .build();
        organizationRepository.save(newOrg);

        // 4. Execution: Create and save the new Admin User
        User newUser = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(rawPassword) // (Note: We will add security encryption to this later!)
                .organization(newOrg) // This is where the Foreign Key is linked!
                .build();

        return userRepository.save(newUser);
    }
}