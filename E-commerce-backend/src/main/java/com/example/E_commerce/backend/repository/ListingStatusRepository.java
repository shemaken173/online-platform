package com.example.E_commerce.backend.repository;

import com.example.E_commerce.backend.model.ListingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ListingStatusRepository extends JpaRepository<ListingStatus, Long> {

    // Find status by name
    Optional<ListingStatus> findByStatusName(String statusName);

    // Check if status exists by name
    boolean existsByStatusName(String statusName);
}
