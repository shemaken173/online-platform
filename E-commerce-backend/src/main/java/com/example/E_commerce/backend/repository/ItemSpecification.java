package com.example.E_commerce.backend.repository;

import com.example.E_commerce.backend.model.Item;
import org.springframework.data.jpa.domain.Specification;

public class ItemSpecification {

    // Filter by Category ID
    public static Specification<Item> hasCategory(Long categoryId) {
        return (root, query, criteriaBuilder) -> {
            if (categoryId == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("category").get("categoryId"), categoryId);
        };
    }

    // Filter by Listing Status ID
    public static Specification<Item> hasStatus(Long statusId) {
        return (root, query, criteriaBuilder) -> {
            if (statusId == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("status").get("statusId"), statusId);
        };
    }

    // Search by title or description keyword (case-insensitive)
    public static Specification<Item> titleOrDescriptionContains(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (keyword == null || keyword.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            String likePattern = "%" + keyword.trim().toLowerCase() + "%";
            return criteriaBuilder.or(
                criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), likePattern),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), likePattern)
            );
        };
    }
}
