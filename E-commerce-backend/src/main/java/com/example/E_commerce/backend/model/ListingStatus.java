package com.example.E_commerce.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "listing_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListingStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusId;

    @Column(nullable = false, unique = true)
    private String statusName;
}
