package com.example.demo.service;

import com.example.demo.entity.Ticket;
import com.example.demo.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService{
    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public List<Ticket> getAllTickets(){
        return ticketRepository.findAll();
    }

    @Override
    public Ticket getTicketByID(Long ticketID){
        return ticketRepository.findById(ticketID).orElse(null);
    }

    @Override
    public Ticket createNewTicket(Ticket ticket){
        return ticketRepository.save(ticket);
    }

    @Override
    public void deleteTicketByID(Long ticketID){
        ticketRepository.deleteById(ticketID);
    }
}
