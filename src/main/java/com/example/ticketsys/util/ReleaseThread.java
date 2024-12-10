package com.example.ticketsys.util;

import com.example.ticketsys.service.TicketService;

public class ReleaseThread extends Thread {

    private final TicketService ticketService;

    public ReleaseThread(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            ticketService.addTickets(1);
            try {
                Thread.sleep(2000); // Release interval
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
