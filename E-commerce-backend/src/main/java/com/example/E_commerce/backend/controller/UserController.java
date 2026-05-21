package com.example.E_commerce.backend.controller;

import com.example.E_commerce.backend.model.User;
import com.example.E_commerce.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Create a new user
     * POST /api/users
     */
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    /**
     * Get user by ID
     * GET /api/users/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    /**
     * Get all users
     * GET /api/users
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Get all active users
     * GET /api/users/active/list
     */
    @GetMapping("/active/list")
    public ResponseEntity<List<User>> getAllActiveUsers() {
        List<User> activeUsers = userService.getAllActiveUsers();
        return ResponseEntity.ok(activeUsers);
    }

    /**
     * Update user
     * PUT /api/users/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user ID");
            }
            User updatedUser = userService.updateUser(id, userDetails);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    /**
     * Delete user by ID (soft delete)
     * DELETE /api/users/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (!userService.userExists(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    /**
     * Check if user exists
     * GET /api/users/{id}/exists
     */
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> userExists(@PathVariable Long id) {
        boolean exists = userService.userExists(id);
        return ResponseEntity.ok(exists);
    }
}
