package com.example.E_commerce.backend.controller;

import com.example.E_commerce.backend.model.ContactMethod;
import com.example.E_commerce.backend.service.ContactMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contact-methods")
public class ContactMethodController {

    @Autowired
    private ContactMethodService contactMethodService;

    /**
     * Create a new contact method
     * POST /api/contact-methods
     */
    @PostMapping
    public ResponseEntity<?> createContactMethod(@RequestBody ContactMethod contactMethod) {
        try {
            ContactMethod createdMethod = contactMethodService.createContactMethod(contactMethod);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdMethod);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    /**
     * Get contact method by ID
     * GET /api/contact-methods/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getContactMethodById(@PathVariable Long id) {
        try {
            Optional<ContactMethod> method = contactMethodService.getContactMethodById(id);
            if (method.isPresent()) {
                return ResponseEntity.ok(method.get());
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contact method not found");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    /**
     * Get all contact methods
     * GET /api/contact-methods
     */
    @GetMapping
    public ResponseEntity<List<ContactMethod>> getAllContactMethods() {
        List<ContactMethod> methods = contactMethodService.getAllContactMethods();
        return ResponseEntity.ok(methods);
    }

    /**
     * Update contact method
     * PUT /api/contact-methods/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateContactMethod(@PathVariable Long id, @RequestBody ContactMethod methodDetails) {
        try {
            ContactMethod updatedMethod = contactMethodService.updateContactMethod(id, methodDetails);
            return ResponseEntity.ok(updatedMethod);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    /**
     * Delete contact method by ID
     * DELETE /api/contact-methods/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContactMethod(@PathVariable Long id) {
        try {
            contactMethodService.deleteContactMethod(id);
            return ResponseEntity.ok("Contact method deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    /**
     * Check if contact method exists by ID
     * GET /api/contact-methods/{id}/exists
     */
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> contactMethodExists(@PathVariable Long id) {
        boolean exists = contactMethodService.contactMethodExists(id);
        return ResponseEntity.ok(exists);
    }
}
