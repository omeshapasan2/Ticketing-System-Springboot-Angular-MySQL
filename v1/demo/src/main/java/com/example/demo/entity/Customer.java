package com.example.demo.entity;

// Importing necessary annotations from JPA and Lombok
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

// Annotation to mark this class as a JPA entity
@Entity
// These 2 annotations automatically generate getters and setters for the fields
@Getter
@Setter
public class Customer {
    //specifies this field as the primary key
    @Id
    //id field will be generated automatically
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Creates id field in the database table

    private String name; // Creates name field in the database table
    private String email; // Creates email field in the database table
    private String password; // Creates password field in the database table
}
