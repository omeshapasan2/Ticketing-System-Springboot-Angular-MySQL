package com.example.demo.service;

import com.example.demo.entity.Event;
import com.example.demo.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService{
    @Autowired
    private EventRepository eventRepository;

    @Override
    public List<Event> getAllEvents(){
        return eventRepository.findAll();
    }

    @Override
    public Event getEventByID(Long eventID){
        return eventRepository.findById(eventID).orElse(null);
    }

    @Override
    public Event createNewEvent(Event event){
        return eventRepository.save(event);
    }

    @Override
    public void deleteEventByID(Long eventID){
        eventRepository.deleteById(eventID);
    }
}
