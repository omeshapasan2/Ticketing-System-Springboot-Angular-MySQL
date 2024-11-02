package com.example.demo.controller;

// Importing necessary classes
import com.example.demo.entity.Customer; // Importing the Customer entity
import com.example.demo.service.CustomerService; // Importing the CustomerService interface
import org.springframework.beans.factory.annotation.Autowired; // Importing the Autowired annotation
import org.springframework.http.ResponseEntity; // Importing ResponseEntity for HTTP responses
import org.springframework.web.bind.annotation.*; // Importing Spring Web annotations

import java.util.List; // Importing the List interface

// Marks this class as a REST controller, enabling it to handle HTTP requests and responses.
@RestController
@CrossOrigin(origins = "*") // Allows cross-origin requests from any origin
public class CustomerController {

    // The CustomerService is automatically injected into this class to handle customer operations.
    @Autowired
    private CustomerService customerService;

    // Handles GET requests to retrieve all customers.
    @GetMapping("/getallcustomers")
    public List<Customer> GetAllCustomers(){
        // Calls the service method to get all customers and returns the list.
        return customerService.getAllCustomers();
    }

    // Handles GET requests to retrieve a customer by their ID.
    @GetMapping("/getcustomerbyid/{id}")
    public Customer GetCustomerByID(@PathVariable Long id){
        // Calls the service method to get a customer by ID and returns the result.
        return customerService.getCustomersByID(id);
    }

    // Handles POST requests to create a new customer.
    @PostMapping("/createcustomer")
    public ResponseEntity<Customer> CreateNewCustomer(@RequestBody Customer customer){
        // Calls the service method to create a new customer and stores the result.
        Customer createNewCustomer = customerService.createNewCustomer(customer);
        // Returns a 201 Created response with the created customer in the body.
        return ResponseEntity.status(201).body(createNewCustomer);
    }

    // Handles DELETE requests to remove a customer by their ID.
    @DeleteMapping("/deletecustomerbyid/{id}")
    public void DeleteCustomerByID(@PathVariable Long id){
        // Calls the service method to delete the customer by ID.
        customerService.deleteCustomerByID(id);
    }

    //  Handles PUT requests to update customer by their ID.
    @PutMapping("/updatecustomerbyid/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer customer){
        return customerService.updateCustomer(id, customer);
    }
}
