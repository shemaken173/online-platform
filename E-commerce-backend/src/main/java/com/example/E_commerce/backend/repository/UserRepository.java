package com.example.E_commerce.backend.repository;

import com.example.E_commerce.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find all active users
    List<User> findByIsActiveTrue();

    // Find user by email
    java.util.Optional<User> findByEmail(String email);

    // Find active user by email
    java.util.Optional<User> findByEmailAndIsActiveTrue(String email);
}
