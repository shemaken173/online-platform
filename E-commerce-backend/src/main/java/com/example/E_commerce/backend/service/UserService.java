package com.example.E_commerce.backend.service;

import com.example.E_commerce.backend.model.User;
import com.example.E_commerce.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Create a new user
    public User createUser(User user) {
        // Validate email
        validateEmail(user.getEmail());

        // Check if email already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Validate and hash password
        validatePassword(user.getPasswordHash());
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));

        // Validate display name
        validateDisplayName(user.getDisplayName());

        // Set default values
        user.setCreatedAt(LocalDateTime.now());
        user.setIsActive(true);

        return userRepository.save(user);
    }

    // Email validation
    private void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!email.matches(emailRegex)) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    // Password validation
    private void validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        if (password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }

        if (!password.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("Password must contain at least one uppercase letter");
        }

        if (!password.matches(".*[a-z].*")) {
            throw new IllegalArgumentException("Password must contain at least one lowercase letter");
        }

        if (!password.matches(".*\\d.*")) {
            throw new IllegalArgumentException("Password must contain at least one number");
        }

        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\",./<>?].*")) {
            throw new IllegalArgumentException("Password must contain at least one special character (!@#$%^&*...)");
        }
    }

    // Display name validation
    private void validateDisplayName(String displayName) {
        if (displayName == null || displayName.trim().isEmpty()) {
            throw new IllegalArgumentException("Display name cannot be empty");
        }

        if (displayName.length() < 2) {
            throw new IllegalArgumentException("Display name must be at least 2 characters long");
        }

        if (displayName.length() > 100) {
            throw new IllegalArgumentException("Display name cannot exceed 100 characters");
        }
    }

    // Get user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Get all active users (default behavior)
    public List<User> getAllUsers() {
        return userRepository.findByIsActiveTrue();
    }

    // Get all active users
    public List<User> getAllActiveUsers() {
        return userRepository.findByIsActiveTrue();
    }

    // Update user with validation
    public User updateUser(Long id, User userDetails) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        Optional<User> existingUser = userRepository.findById(id);
        if (!existingUser.isPresent()) {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }

        User user = existingUser.get();

        // Validate and update email if provided and different
        if (userDetails.getEmail() != null && !userDetails.getEmail().isEmpty() && !userDetails.getEmail().equals(user.getEmail())) {
            validateEmail(userDetails.getEmail());
            if (userRepository.findByEmail(userDetails.getEmail()).isPresent()) {
                throw new IllegalArgumentException("Email already exists");
            }
            user.setEmail(userDetails.getEmail());
        }

        // Validate and update display name if provided
        if (userDetails.getDisplayName() != null && !userDetails.getDisplayName().isEmpty()) {
            validateDisplayName(userDetails.getDisplayName());
            user.setDisplayName(userDetails.getDisplayName());
        }

        // Validate and hash password if provided
        if (userDetails.getPasswordHash() != null && !userDetails.getPasswordHash().isEmpty()) {
            validatePassword(userDetails.getPasswordHash());
            user.setPasswordHash(passwordEncoder.encode(userDetails.getPasswordHash()));
        }

        // Update active status if provided
        if (userDetails.getIsActive() != null) {
            user.setIsActive(userDetails.getIsActive());
        }

        return userRepository.save(user);
    }

    // Delete user by ID (soft delete - marks as inactive)
    public void deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User userToDelete = user.get();
            userToDelete.setIsActive(false);
            userRepository.save(userToDelete);
        }
    }

    // Check if user exists by ID
    public boolean userExists(Long id) {
        return userRepository.existsById(id);
    }
}
