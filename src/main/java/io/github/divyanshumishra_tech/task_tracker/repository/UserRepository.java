package io.github.divyanshumishra_tech.task_tracker.repository;

import io.github.divyanshumishra_tech.task_tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findByOrganizationId(Long organizationId);
}