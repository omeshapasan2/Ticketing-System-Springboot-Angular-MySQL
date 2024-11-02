package com.example.demo.service;

import com.example.demo.entity.TicketPool;
import com.example.demo.repository.TicketPoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketPoolServiceImpl implements TicketPoolService{

    @Autowired
    private TicketPoolRepository ticketPoolRepository;

    @Override
    public List<TicketPool> getAllTicketPools(){
        return ticketPoolRepository.findAll();
    }

    @Override
    public TicketPool getTicketPoolByID(Long poolID){
        return ticketPoolRepository.findById(poolID).orElse(null);
    }

    @Override
    public TicketPool createNewTicketPool(TicketPool ticketPool){
        return ticketPoolRepository.save(ticketPool);
    }

    @Override
    public void deleteTicketPoolByID(Long poolID){
        ticketPoolRepository.deleteById(poolID);
    }
}
