package com.example.demo.service;

// Importing necessary classes and annotations
import com.example.demo.entity.Customer; // Importing the Customer entity
import org.springframework.stereotype.Service; // Importing the Service annotation

import java.util.List; // Importing the List interface

// Annotation to indicate that this interface is a service component in Spring
@Service
public interface CustomerService {

    // Method to retrieve all customers
    List<Customer> getAllCustomers();

    // Method to retrieve a customer by their ID
    Customer getCustomersByID(Long id);

    // Method to create a new customer
    Customer createNewCustomer(Customer customer);

    // Method to delete a customer by their ID
    void deleteCustomerByID(Long id);

    // Method to update by their ID
    Customer updateCustomer (Long ID  , Customer customer);
}
