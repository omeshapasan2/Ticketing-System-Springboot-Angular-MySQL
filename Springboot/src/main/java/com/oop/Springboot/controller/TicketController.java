package com.oop.Springboot.controller;

import com.oop.Springboot.entity.Ticket;
import com.oop.Springboot.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    // Endpoint to book a ticket (for customer)
    @PostMapping("/book/{ticketId}/{customerId}")
    public ResponseEntity<Ticket> bookTicket(@PathVariable Long ticketId, @PathVariable Long customerId) {
        Ticket bookedTicket = ticketService.bookTicket(ticketId, customerId);
        return ResponseEntity.ok(bookedTicket);
    }

    // Endpoint to get all tickets for a vendor
    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<Ticket>> getTicketsByVendor(@PathVariable Long vendorId) {
        List<Ticket> tickets = ticketService.getTicketsByVendor(vendorId);
        return ResponseEntity.ok(tickets);
    }

    // Endpoint to get all tickets booked by a customer
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Ticket>> getTicketsByCustomer(@PathVariable Long customerId) {
        List<Ticket> tickets = ticketService.getTicketsByCustomer(customerId);
        return ResponseEntity.ok(tickets);
    }
}

