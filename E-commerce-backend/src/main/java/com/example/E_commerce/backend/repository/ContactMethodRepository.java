package com.example.E_commerce.backend.repository;

import com.example.E_commerce.backend.model.ContactMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactMethodRepository extends JpaRepository<ContactMethod, Long> {

    // Find contact method by type
    Optional<ContactMethod> findByMethodType(String methodType);

    // Check if contact method exists by type
    boolean existsByMethodType(String methodType);
}
