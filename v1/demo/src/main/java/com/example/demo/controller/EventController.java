package com.example.demo.controller;

import com.example.demo.entity.Event;
import com.example.demo.entity.TicketPool;
import com.example.demo.service.EventService;
import com.example.demo.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/GetAllEvents")
    public List<Event> GetAllEvents(){
        return eventService.getAllEvents();
    }

    @GetMapping("/GetEventByID")
    public Event GetEventByID(@PathVariable Long eventID){
        return eventService.getEventByID(eventID);
    }

    @PostMapping("/CreateNewEvent")
    public ResponseEntity<Event> CreateNewEvent(@RequestBody Event event){
        Event createNewEvent = eventService.createNewEvent(event);
        return ResponseEntity.status(201).body(createNewEvent);
    }

    @DeleteMapping("/DeleteEventByID")
    public void DeleteEventByID(@PathVariable Long eventID){
        eventService.deleteEventByID(eventID);
    }

}
