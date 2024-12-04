package com.oop.Springboot.ticketingcli;

public class Ticket {
    private final int ticketNumber;

    public Ticket(int ticketNumber){
        this.ticketNumber=ticketNumber;
    }

    public int getTicketNumber(){
        return ticketNumber;
    }

    @Override
    public String toString(){
        return String.valueOf(ticketNumber);
    }
}
