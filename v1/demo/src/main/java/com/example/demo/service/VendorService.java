package com.example.demo.service;

// Importing necessary classes and annotations
import com.example.demo.entity.Vendors; // Importing the Vendors entity
import org.springframework.stereotype.Service; // Importing the Service annotation

import java.util.List; // Importing the List interface

// Annotation to indicate that this interface is a service component in Spring
@Service
public interface VendorService {

    // Method to retrieve all vendors
    List<Vendors> getAllVendors();

    // Method to retrieve a vendor by their ID
    Vendors getVendorsByID(Long id);

    // Method to create a new vendor
    Vendors createNewVendor(Vendors vendors);

    // Method to delete a vendor by their ID
    void deleteVendorByID(Long id);

    // Method to update a vendor by their ID
    Vendors updateVendor (Long id , Vendors vendors);
}
