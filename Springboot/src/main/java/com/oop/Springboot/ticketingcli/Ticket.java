package com.oop.Springboot.ticketingcli;

public class Ticket {
    private final int ticketNumber;

    /**
     * constructor to create a ticket with a specific number.
     * @param ticketNumber the number assigned to the ticket
     */
    public Ticket(int ticketNumber){
        this.ticketNumber = ticketNumber;
    }

    /**
     * retrieves the ticket number.
     * @return the ticket number
     */
    public int getTicketNumber(){
        return ticketNumber;
    }

    /**
     * returns the ticket number as a string.
     * @return the string representation of the ticket number
     */
    @Override
    public String toString(){
        return String.valueOf(ticketNumber);
    }
}
