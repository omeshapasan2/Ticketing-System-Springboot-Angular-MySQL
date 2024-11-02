package com.example.demo.controller;

import com.example.demo.entity.TicketPool;
import com.example.demo.service.TicketPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class TicketPoolController {

    @Autowired
    private TicketPoolService ticketPoolService;

    @GetMapping("/getallticketpools")
    public List<TicketPool> GetAllTicketPools(){
        return ticketPoolService.getAllTicketPools();
    }

    @GetMapping("/GetTicketPoolByID")
    public TicketPool GetTicketPoolByID(@PathVariable Long poolID){
        return ticketPoolService.getTicketPoolByID(poolID);
    }

    @PostMapping("/CreateNewTicketPool")
    public ResponseEntity<TicketPool> CreateNewTicketPool(@RequestBody TicketPool ticketPool){
        TicketPool createNewTicketPool = ticketPoolService.createNewTicketPool(ticketPool);
        return ResponseEntity.status(201).body(createNewTicketPool);
    }

    @DeleteMapping("/DeleteTicketPoolByID")
    public void DeleteTicketPoolByID(@PathVariable Long poolID){
        ticketPoolService.deleteTicketPoolByID(poolID);
    }
}
