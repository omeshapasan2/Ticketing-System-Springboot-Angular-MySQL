package com.oop.Springboot.repository;

import com.oop.Springboot.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    // Find a ticket by its ID
    Ticket findById(long id);

    // Save a ticket (Spring Data JPA automatically implements this)
    // You don't need to write the implementation as it's handled by JpaRepository

    // Find all tickets created by a specific vendor
    List<Ticket> findByVendorId(Long vendorId);

    // Find all tickets booked by a specific customer
    List<Ticket> findByCustomerId(Long customerId);
}
