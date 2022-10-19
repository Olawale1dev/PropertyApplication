package com.example.property.repository;

import com.example.property.controller.Attempts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttemptsRepository extends JpaRepository<Attempts, Integer > {
    Optional<Attempts> findAttemptsByUsername(String username);
}
