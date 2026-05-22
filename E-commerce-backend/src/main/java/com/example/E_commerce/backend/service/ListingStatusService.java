package com.example.E_commerce.backend.service;

import com.example.E_commerce.backend.model.ListingStatus;
import com.example.E_commerce.backend.repository.ListingStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ListingStatusService {

    @Autowired
    private ListingStatusRepository listingStatusRepository;

    // Create a new listing status
    public ListingStatus createListingStatus(ListingStatus listingStatus) {
        // Validate status name
        validateStatusName(listingStatus.getStatusName());

        // Check if status name already exists
        if (listingStatusRepository.existsByStatusName(listingStatus.getStatusName())) {
            throw new IllegalArgumentException("Listing status with name '" + listingStatus.getStatusName() + "' already exists");
        }

        return listingStatusRepository.save(listingStatus);
    }

    // Status name validation
    private void validateStatusName(String statusName) {
        if (statusName == null || statusName.trim().isEmpty()) {
            throw new IllegalArgumentException("Status name cannot be empty");
        }

        if (statusName.length() < 2) {
            throw new IllegalArgumentException("Status name must be at least 2 characters long");
        }

        if (statusName.length() > 50) {
            throw new IllegalArgumentException("Status name cannot exceed 50 characters");
        }
    }

    // Get listing status by ID
    public Optional<ListingStatus> getListingStatusById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid listing status ID");
        }
        return listingStatusRepository.findById(id);
    }

    // Get all listing statuses
    public List<ListingStatus> getAllListingStatuses() {
        return listingStatusRepository.findAll();
    }

    // Update listing status
    public ListingStatus updateListingStatus(Long id, ListingStatus statusDetails) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid listing status ID");
        }

        Optional<ListingStatus> existingStatus = listingStatusRepository.findById(id);
        if (!existingStatus.isPresent()) {
            throw new IllegalArgumentException("Listing status not found with ID: " + id);
        }

        ListingStatus status = existingStatus.get();

        // Validate and update status name if provided and different
        if (statusDetails.getStatusName() != null && !statusDetails.getStatusName().isEmpty() && !statusDetails.getStatusName().equals(status.getStatusName())) {
            validateStatusName(statusDetails.getStatusName());
            if (listingStatusRepository.existsByStatusName(statusDetails.getStatusName())) {
                throw new IllegalArgumentException("Listing status with name '" + statusDetails.getStatusName() + "' already exists");
            }
            status.setStatusName(statusDetails.getStatusName());
        }

        return listingStatusRepository.save(status);
    }

    // Delete listing status by ID
    public void deleteListingStatus(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid listing status ID");
        }

        if (!listingStatusRepository.existsById(id)) {
            throw new IllegalArgumentException("Listing status not found with ID: " + id);
        }

        listingStatusRepository.deleteById(id);
    }

    // Check if listing status exists by ID
    public boolean listingStatusExists(Long id) {
        if (id == null || id <= 0) {
            return false;
        }
        return listingStatusRepository.existsById(id);
    }
}
