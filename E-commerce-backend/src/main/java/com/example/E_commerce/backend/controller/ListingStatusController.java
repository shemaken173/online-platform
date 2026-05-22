package com.example.E_commerce.backend.controller;

import com.example.E_commerce.backend.model.ListingStatus;
import com.example.E_commerce.backend.service.ListingStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/listing-statuses")
public class ListingStatusController {

    @Autowired
    private ListingStatusService listingStatusService;

    /**
     * Create a new listing status
     * POST /api/listing-statuses
     */
    @PostMapping
    public ResponseEntity<?> createListingStatus(@RequestBody ListingStatus listingStatus) {
        try {
            ListingStatus createdStatus = listingStatusService.createListingStatus(listingStatus);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdStatus);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    /**
     * Get listing status by ID
     * GET /api/listing-statuses/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getListingStatusById(@PathVariable Long id) {
        try {
            Optional<ListingStatus> status = listingStatusService.getListingStatusById(id);
            if (status.isPresent()) {
                return ResponseEntity.ok(status.get());
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Listing status not found");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    /**
     * Get all listing statuses
     * GET /api/listing-statuses
     */
    @GetMapping
    public ResponseEntity<List<ListingStatus>> getAllListingStatuses() {
        List<ListingStatus> statuses = listingStatusService.getAllListingStatuses();
        return ResponseEntity.ok(statuses);
    }

    /**
     * Update listing status
     * PUT /api/listing-statuses/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateListingStatus(@PathVariable Long id, @RequestBody ListingStatus statusDetails) {
        try {
            ListingStatus updatedStatus = listingStatusService.updateListingStatus(id, statusDetails);
            return ResponseEntity.ok(updatedStatus);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    /**
     * Delete listing status by ID
     * DELETE /api/listing-statuses/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteListingStatus(@PathVariable Long id) {
        try {
            listingStatusService.deleteListingStatus(id);
            return ResponseEntity.ok("Listing status deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    /**
     * Check if listing status exists by ID
     * GET /api/listing-statuses/{id}/exists
     */
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> listingStatusExists(@PathVariable Long id) {
        boolean exists = listingStatusService.listingStatusExists(id);
        return ResponseEntity.ok(exists);
    }
}
