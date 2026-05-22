package com.example.E_commerce.backend.service;

import com.example.E_commerce.backend.model.Item;
import com.example.E_commerce.backend.model.User;
import com.example.E_commerce.backend.model.Category;
import com.example.E_commerce.backend.model.ListingStatus;
import com.example.E_commerce.backend.model.ContactMethod;
import com.example.E_commerce.backend.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    // Create a new item
    public Item createItem(Item item) {
        // Validate required fields
        validateTitle(item.getTitle());
        validatePrice(item.getPrice());
        validateIsFree(item.getIsFree());
        validateUser(item.getUser());
        validateCategory(item.getCategory());
        validateStatus(item.getStatus());
        validateContactMethod(item.getContactMethod());
        validateCondition(item.getCondition());

        // Check if item title already exists
        if (itemRepository.existsByTitle(item.getTitle())) {
            throw new IllegalArgumentException("Item with title '" + item.getTitle() + "' already exists");
        }

        // Set default values
        item.setCreatedAt(LocalDateTime.now());
        item.setUpdatedAt(LocalDateTime.now());

        return itemRepository.save(item);
    }

    // Item title validation
    private void validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Item title cannot be empty");
        }

        if (title.length() < 3) {
            throw new IllegalArgumentException("Item title must be at least 3 characters long");
        }

        if (title.length() > 255) {
            throw new IllegalArgumentException("Item title cannot exceed 255 characters");
        }
    }

    // Price validation
    private void validatePrice(BigDecimal price) {
        if (price == null) {
            throw new IllegalArgumentException("Price cannot be null");
        }

        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }

        if (price.scale() > 2) {
            throw new IllegalArgumentException("Price can have maximum 2 decimal places");
        }
    }

    // IsFree validation
    private void validateIsFree(Boolean isFree) {
        if (isFree == null) {
            throw new IllegalArgumentException("IsFree cannot be null");
        }
    }

    // User validation
    private void validateUser(User user) {
        if (user == null || user.getUserId() == null || user.getUserId() <= 0) {
            throw new IllegalArgumentException("Invalid user");
        }
    }

    // Category validation
    private void validateCategory(Category category) {
        if (category == null || category.getCategoryId() == null || category.getCategoryId() <= 0) {
            throw new IllegalArgumentException("Invalid category");
        }
    }

    // Status validation
    private void validateStatus(ListingStatus status) {
        if (status == null || status.getStatusId() == null || status.getStatusId() <= 0) {
            throw new IllegalArgumentException("Invalid listing status");
        }
    }

    // Contact Method validation
    private void validateContactMethod(ContactMethod contactMethod) {
        if (contactMethod == null || contactMethod.getContactMethodId() == null || contactMethod.getContactMethodId() <= 0) {
            throw new IllegalArgumentException("Invalid contact method");
        }
    }

    // Condition validation
    private void validateCondition(String condition) {
        if (condition == null || condition.trim().isEmpty()) {
            throw new IllegalArgumentException("Condition cannot be empty");
        }

        String[] validConditions = {"New", "Like New", "Good", "Fair", "Poor"};
        boolean isValid = false;
        for (String valid : validConditions) {
            if (valid.equalsIgnoreCase(condition)) {
                isValid = true;
                break;
            }
        }

        if (!isValid) {
            throw new IllegalArgumentException("Condition must be one of: New, Like New, Good, Fair, Poor");
        }
    }

    // Get item by ID
    public Optional<Item> getItemById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid item ID");
        }
        return itemRepository.findById(id);
    }

    // Get item by title
    public Optional<Item> getItemByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Item title cannot be empty");
        }
        return itemRepository.findByTitle(title);
    }

    // Get all items
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    // Update item
    public Item updateItem(Long id, Item itemDetails) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid item ID");
        }

        Optional<Item> existingItem = itemRepository.findById(id);
        if (!existingItem.isPresent()) {
            throw new IllegalArgumentException("Item not found with ID: " + id);
        }

        Item item = existingItem.get();

        // Validate and update item title if provided and different
        if (itemDetails.getTitle() != null && !itemDetails.getTitle().isEmpty() && !itemDetails.getTitle().equals(item.getTitle())) {
            validateTitle(itemDetails.getTitle());
            if (itemRepository.existsByTitle(itemDetails.getTitle())) {
                throw new IllegalArgumentException("Item with title '" + itemDetails.getTitle() + "' already exists");
            }
            item.setTitle(itemDetails.getTitle());
        }

        // Update description if provided
        if (itemDetails.getDescription() != null) {
            item.setDescription(itemDetails.getDescription());
        }

        // Update condition if provided
        if (itemDetails.getCondition() != null && !itemDetails.getCondition().isEmpty()) {
            validateCondition(itemDetails.getCondition());
            item.setCondition(itemDetails.getCondition());
        }

        // Update price if provided
        if (itemDetails.getPrice() != null) {
            validatePrice(itemDetails.getPrice());
            item.setPrice(itemDetails.getPrice());
        }

        // Update is free if provided
        if (itemDetails.getIsFree() != null) {
            validateIsFree(itemDetails.getIsFree());
            item.setIsFree(itemDetails.getIsFree());
        }

        // Update pickup location if provided
        if (itemDetails.getPickupLocation() != null) {
            item.setPickupLocation(itemDetails.getPickupLocation());
        }

        // Update image URL if provided
        if (itemDetails.getImageUrl() != null) {
            item.setImageUrl(itemDetails.getImageUrl());
        }

        // Update expire at if provided
        if (itemDetails.getExpireAt() != null) {
            item.setExpireAt(itemDetails.getExpireAt());
        }

        // Update status if provided
        if (itemDetails.getStatus() != null) {
            validateStatus(itemDetails.getStatus());
            item.setStatus(itemDetails.getStatus());
        }

        // Update contact method if provided
        if (itemDetails.getContactMethod() != null) {
            validateContactMethod(itemDetails.getContactMethod());
            item.setContactMethod(itemDetails.getContactMethod());
        }

        item.setUpdatedAt(LocalDateTime.now());

        return itemRepository.save(item);
    }

    // Delete item by ID
    public void deleteItem(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid item ID");
        }

        if (!itemRepository.existsById(id)) {
            throw new IllegalArgumentException("Item not found with ID: " + id);
        }

        itemRepository.deleteById(id);
    }

    // Check if item exists by ID
    public boolean itemExists(Long id) {
        if (id == null || id <= 0) {
            return false;
        }
        return itemRepository.existsById(id);
    }

    // Check if item exists by title
    public boolean itemExistsByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return false;
        }
        return itemRepository.existsByTitle(title);
    }
}
