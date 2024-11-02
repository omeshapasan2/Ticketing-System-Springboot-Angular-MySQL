package com.example.demo.service;

import com.example.demo.entity.Ticket;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TicketService {
    List<Ticket> getAllTickets();

    Ticket getTicketByID(Long ticketID);

    Ticket createNewTicket(Ticket ticket);

    void deleteTicketByID(Long ticketID);
}
