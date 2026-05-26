package com.example.E_commerce.backend.repository;

import com.example.E_commerce.backend.model.Item;
import com.example.E_commerce.backend.model.User;
import com.example.E_commerce.backend.model.Category;
import com.example.E_commerce.backend.model.ListingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {

    // Find items by user
    List<Item> findByUser(User user);

    // Find items by category
    List<Item> findByCategory(Category category);

    // Find items by status
    List<Item> findByStatus(ListingStatus status);

    // Find items by title
    List<Item> findByTitle(String title);

    // Check if item exists by title
    boolean existsByTitle(String title);

    // Find items by user and category
    List<Item> findByUserAndCategory(User user, Category category);

    // Find items by user and status
    List<Item> findByUserAndStatus(User user, ListingStatus status);
}
