package com.example.demo.service;

// Importing necessary classes
import com.example.demo.entity.Customer; // Importing the Customer entity
import com.example.demo.repository.CustomerRepository; // Importing the CustomerRepository interface
import org.springframework.beans.factory.annotation.Autowired; // Importing the Autowired annotation
import org.springframework.stereotype.Service; // Importing the Service annotation

import java.util.List; // Importing the List interface

// Annotation indicating that this class is a service component in Spring
@Service
public class CustomerServiceImpl implements CustomerService {

    // Injecting the CustomerRepository bean
    @Autowired
    private CustomerRepository customerRepository;

    // Method to retrieve all customers
    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll(); // Fetching all customers from the repository
    }

    // Method to retrieve a customer by their ID
    @Override
    public Customer getCustomersByID(Long id) {
        return customerRepository.findById(id).orElse(null); // Returning the customer or null if not found
    }

    // Method to create a new customer
    @Override
    public Customer createNewCustomer(Customer customer) {
        return customerRepository.save(customer); // Saving the new customer to the repository
    }

    // Method to delete a customer by their ID
    @Override
    public void deleteCustomerByID(Long id) {
        customerRepository.deleteById(id); // Deleting the customer from the repository
    }

    @Override
    public Customer updateCustomer(Long id , Customer customer){
        Customer existCustomer = customerRepository.findById(id).orElse(null);
        if (existCustomer !=null){
            existCustomer.setName(customer.getName());
            existCustomer.setEmail(customer.getEmail());
            existCustomer.setPassword(customer.getPassword());
            return customerRepository.save(existCustomer);
        }else {
            return null;
        }
    }
}
