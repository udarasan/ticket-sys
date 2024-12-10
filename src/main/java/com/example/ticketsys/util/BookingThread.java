package com.example.ticketsys.util;

import com.example.ticketsys.service.TicketService;

/**
 * @TimeStamp 2024-12-10 19:16
 * @ProjectDetails ticket sys
 * @Author udarasan
 */


public class BookingThread extends Thread {

    private final TicketService ticketService;

    public BookingThread(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            ticketService.bookTicket();
            try {
                Thread.sleep(1000); // Booking interval
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

