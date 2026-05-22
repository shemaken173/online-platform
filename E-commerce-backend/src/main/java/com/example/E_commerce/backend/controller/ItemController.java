package com.example.E_commerce.backend.controller;

import com.example.E_commerce.backend.model.Item;
import com.example.E_commerce.backend.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * Create a new item
     * POST /api/items
     */
    @PostMapping
    public ResponseEntity<?> createItem(@RequestBody Item item) {
        try {
            Item createdItem = itemService.createItem(item);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    /**
     * Get item by ID
     * GET /api/items/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getItemById(@PathVariable Long id) {
        try {
            Optional<Item> item = itemService.getItemById(id);
            if (item.isPresent()) {
                return ResponseEntity.ok(item.get());
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    /**
     * Get item by title
     * GET /api/items/title/{title}
     */
    @GetMapping("/title/{title}")
    public ResponseEntity<?> getItemByTitle(@PathVariable String title) {
        try {
            Optional<Item> item = itemService.getItemByTitle(title);
            if (item.isPresent()) {
                return ResponseEntity.ok(item.get());
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    /**
     * Get all items
     * GET /api/items
     */
    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> items = itemService.getAllItems();
        return ResponseEntity.ok(items);
    }

    /**
     * Update item
     * PUT /api/items/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateItem(@PathVariable Long id, @RequestBody Item itemDetails) {
        try {
            Item updatedItem = itemService.updateItem(id, itemDetails);
            return ResponseEntity.ok(updatedItem);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    /**
     * Delete item by ID
     * DELETE /api/items/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
        try {
            itemService.deleteItem(id);
            return ResponseEntity.ok("Item deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    /**
     * Check if item exists by ID
     * GET /api/items/{id}/exists
     */
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> itemExists(@PathVariable Long id) {
        boolean exists = itemService.itemExists(id);
        return ResponseEntity.ok(exists);
    }

    /**
     * Check if item exists by title
     * GET /api/items/exists/title/{title}
     */
    @GetMapping("/exists/title/{title}")
    public ResponseEntity<Boolean> itemExistsByTitle(@PathVariable String title) {
        boolean exists = itemService.itemExistsByTitle(title);
        return ResponseEntity.ok(exists);
    }
}
