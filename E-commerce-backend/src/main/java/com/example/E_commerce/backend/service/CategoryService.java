package com.example.E_commerce.backend.service;

import com.example.E_commerce.backend.model.Category;
import com.example.E_commerce.backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Create a new category
    public Category createCategory(Category category) {
        // Validate name
        validateName(category.getName());

        // Check if category name already exists
        if (categoryRepository.existsByName(category.getName())) {
            throw new IllegalArgumentException("Category with name '" + category.getName() + "' already exists");
        }

        return categoryRepository.save(category);
    }

    // Name validation
    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }

        if (name.length() < 2) {
            throw new IllegalArgumentException("Category name must be at least 2 characters long");
        }

        if (name.length() > 50) {
            throw new IllegalArgumentException("Category name cannot exceed 50 characters");
        }
    }

    // Get category by ID
    public Optional<Category> getCategoryById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid category ID");
        }
        return categoryRepository.findById(id);
    }

    // Get category by name
    public Optional<Category> getCategoryByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }
        return categoryRepository.findByName(name);
    }

    // Get all categories
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Update category
    public Category updateCategory(Long id, Category categoryDetails) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid category ID");
        }

        Optional<Category> existingCategory = categoryRepository.findById(id);
        if (!existingCategory.isPresent()) {
            throw new IllegalArgumentException("Category not found with ID: " + id);
        }

        Category category = existingCategory.get();

        // Validate and update name if provided and different
        if (categoryDetails.getName() != null && !categoryDetails.getName().isEmpty() && !categoryDetails.getName().equals(category.getName())) {
            validateName(categoryDetails.getName());
            if (categoryRepository.existsByName(categoryDetails.getName())) {
                throw new IllegalArgumentException("Category with name '" + categoryDetails.getName() + "' already exists");
            }
            category.setName(categoryDetails.getName());
        }

        // Update description if provided
        if (categoryDetails.getDescription() != null) {
            category.setDescription(categoryDetails.getDescription());
        }

        return categoryRepository.save(category);
    }

    // Delete category by ID
    public void deleteCategory(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid category ID");
        }

        if (!categoryRepository.existsById(id)) {
            throw new IllegalArgumentException("Category not found with ID: " + id);
        }

        categoryRepository.deleteById(id);
    }

    // Check if category exists by ID
    public boolean categoryExists(Long id) {
        if (id == null || id <= 0) {
            return false;
        }
        return categoryRepository.existsById(id);
    }

    // Check if category exists by name
    public boolean categoryExistsByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return categoryRepository.existsByName(name);
    }
}
