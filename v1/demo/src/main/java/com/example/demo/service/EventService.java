package com.example.demo.service;

import com.example.demo.entity.Event;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EventService {
    List<Event> getAllEvents();

    Event getEventByID(Long eventID);

    Event createNewEvent(Event event);

    void deleteEventByID(Long eventID);
}
