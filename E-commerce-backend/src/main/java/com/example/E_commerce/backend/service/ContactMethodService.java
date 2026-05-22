package com.example.E_commerce.backend.service;

import com.example.E_commerce.backend.model.ContactMethod;
import com.example.E_commerce.backend.model.User;
import com.example.E_commerce.backend.repository.ContactMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactMethodService {

    @Autowired
    private ContactMethodRepository contactMethodRepository;

    // Create a new contact method
    public ContactMethod createContactMethod(ContactMethod contactMethod) {
        // Validate method type
        validateMethodType(contactMethod.getMethodType());
        validateUser(contactMethod.getUser());
        validateIsActive(contactMethod.getIsActive());

        return contactMethodRepository.save(contactMethod);
    }

    // Method type validation
    private void validateMethodType(String methodType) {
        if (methodType == null || methodType.trim().isEmpty()) {
            throw new IllegalArgumentException("Method type cannot be empty");
        }

        if (methodType.length() < 2) {
            throw new IllegalArgumentException("Method type must be at least 2 characters long");
        }

        if (methodType.length() > 100) {
            throw new IllegalArgumentException("Method type cannot exceed 100 characters");
        }
    }

    // User validation
    private void validateUser(User user) {
        if (user == null || user.getUserId() == null || user.getUserId() <= 0) {
            throw new IllegalArgumentException("Invalid user");
        }
    }

    // IsActive validation
    private void validateIsActive(Boolean isActive) {
        if (isActive == null) {
            throw new IllegalArgumentException("IsActive cannot be null");
        }
    }

    // Get contact method by ID
    public Optional<ContactMethod> getContactMethodById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid contact method ID");
        }
        return contactMethodRepository.findById(id);
    }

    // Get all contact methods
    public List<ContactMethod> getAllContactMethods() {
        return contactMethodRepository.findAll();
    }

    // Update contact method
    public ContactMethod updateContactMethod(Long id, ContactMethod methodDetails) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid contact method ID");
        }

        Optional<ContactMethod> existingMethod = contactMethodRepository.findById(id);
        if (!existingMethod.isPresent()) {
            throw new IllegalArgumentException("Contact method not found with ID: " + id);
        }

        ContactMethod method = existingMethod.get();

        // Validate and update method type if provided and different
        if (methodDetails.getMethodType() != null && !methodDetails.getMethodType().isEmpty() && !methodDetails.getMethodType().equals(method.getMethodType())) {
            validateMethodType(methodDetails.getMethodType());
            method.setMethodType(methodDetails.getMethodType());
        }

        // Update method value if provided
        if (methodDetails.getContactValue() != null) {
            method.setContactValue(methodDetails.getContactValue());
        }

        // Update is active if provided
        if (methodDetails.getIsActive() != null) {
            validateIsActive(methodDetails.getIsActive());
            method.setIsActive(methodDetails.getIsActive());
        }

        return contactMethodRepository.save(method);
    }

    // Delete contact method by ID
    public void deleteContactMethod(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid contact method ID");
        }

        if (!contactMethodRepository.existsById(id)) {
            throw new IllegalArgumentException("Contact method not found with ID: " + id);
        }

        contactMethodRepository.deleteById(id);
    }

    // Check if contact method exists by ID
    public boolean contactMethodExists(Long id) {
        if (id == null || id <= 0) {
            return false;
        }
        return contactMethodRepository.existsById(id);
    }
}
