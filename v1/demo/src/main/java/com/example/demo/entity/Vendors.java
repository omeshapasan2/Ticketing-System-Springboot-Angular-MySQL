package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity     //Defines Entity Class which Class Creates Database Tables.
// ------
@Getter
@Setter
// @Getter and @Setter[Lombok annonations] are used which generates Getters and Setters automatically.

public class Vendors {
    @Id // Defines Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Means that the primary key will be automatically generated by the database
    private Long id;  // Creates id field in the database table

    private String name; // Creates name field in the database table
    private String email; // Creates email field in the database table
    private String password; // Creates password field in the database table
}
