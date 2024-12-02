package com.oop.Springboot.service;

import com.oop.Springboot.entity.Ticket;
import com.oop.Springboot.repository.TicketRepository;
import com.oop.Springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    // Book a ticket (for customer)
    public Ticket bookTicket(Long ticketId, Long customerId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));

        if (ticket.isBooked()) {
            throw new RuntimeException("Ticket is already booked");
        }

        // Set customer and mark ticket as booked
        ticket.setCustomer(userRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found")));
        ticket.setBooked(true);

        return ticketRepository.save(ticket);
    }

    // Get all tickets by vendor
    public List<Ticket> getTicketsByVendor(Long vendorId) {
        return ticketRepository.findByVendorId(vendorId);
    }

    // Get all tickets booked by a customer
    public List<Ticket> getTicketsByCustomer(Long customerId) {
        return ticketRepository.findByCustomerId(customerId);
    }
}

