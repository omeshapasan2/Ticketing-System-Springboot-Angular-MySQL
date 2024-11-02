package com.example.demo.controller;

import com.example.demo.entity.Ticket;
import com.example.demo.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @GetMapping("/getalltickets")
    public List<Ticket> GetAllTickets(){
        return ticketService.getAllTickets();
    }

    @PostMapping("/createnewticket")
    public ResponseEntity<Ticket> CreateNewTicket(@RequestBody Ticket ticket){
        Ticket createNewTicket = ticketService.createNewTicket(ticket);
        return ResponseEntity.status(201).body(createNewTicket);
    }

    @GetMapping("/getticketbyid")
    public Ticket GetTicketByID(@PathVariable Long ticketID){
        return ticketService.getTicketByID(ticketID);
    }

    @DeleteMapping("/deleteticketbyid")
    public void DeleteCustomerByID(@PathVariable Long ticketID){
        ticketService.deleteTicketByID(ticketID);
    }
}
