package com.example.demo.service;

// Importing necessary classes
import com.example.demo.entity.Vendors; // Importing the Vendors entity
import com.example.demo.repository.VendorRepository; // Importing the VendorRepository interface
import org.springframework.beans.factory.annotation.Autowired; // Importing the Autowired annotation
import org.springframework.stereotype.Service; // Importing the Service annotation

import java.util.List; // Importing the List interface

// Marks this class as a Spring service component, meaning it contains business logic.
@Service
public class VendorServiceImpl implements VendorService {

    // The VendorRepository is automatically injected into this class to handle database operations.
    @Autowired
    private VendorRepository vendorRepository;

    // Fetches and returns a list of all Vendors from the database.
    @Override
    public List<Vendors> getAllVendors() {
        return vendorRepository.findAll(); // Using the repository to get all vendors
    }

    // Retrieves a Vendor by their ID from the database.
    @Override
    public Vendors getVendorsByID(Long id) {
        // If the Vendor is not found, it returns null.
        return vendorRepository.findById(id).orElse(null); // Fetching vendor by ID
    }

    // Saves the provided Vendors entity to the database and returns the saved entity.
    @Override
    public Vendors createNewVendor(Vendors vendors) {
        return vendorRepository.save(vendors); // Persisting the new vendor
    }

    // Deletes a Vendor by their ID.
    @Override
    public void deleteVendorByID(Long id) {
        vendorRepository.deleteById(id); // Deleting the vendor from the database
    }

    // Updates a vendor by their ID
    @Override
    public Vendors updateVendor(Long id , Vendors vendors) {
        Vendors existVendor = vendorRepository.findById(id).orElse(null);

        if (existVendor != null) {
            existVendor.setName(vendors.getName());
            existVendor.setEmail(vendors.getEmail());
            existVendor.setPassword(vendors.getPassword());
            return vendorRepository.save(existVendor);
        } else {
            return null;
        }
    }

}
