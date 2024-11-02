package com.example.demo.service;

import com.example.demo.entity.TicketPool;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TicketPoolService {

    List<TicketPool> getAllTicketPools();

    TicketPool getTicketPoolByID(Long poolID);

    TicketPool createNewTicketPool(TicketPool ticketPool);

    void deleteTicketPoolByID(Long poolID);

}
