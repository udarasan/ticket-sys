package com.example.ticketsys.util;

/**
 * @TimeStamp 2024-12-10 19:25
 * @ProjectDetails ticket sys
 * @Author udarasan
 */

import com.example.ticketsys.service.TicketService;
import org.springframework.stereotype.Component;

@Component
public class ThreadManager {

    private final BookingThread bookingThread;
    private final ReleaseThread releaseThread;

    public ThreadManager(TicketService ticketService) {
        this.bookingThread = new BookingThread(ticketService);
        this.releaseThread = new ReleaseThread(ticketService);
    }

    public void startThreads() {
        bookingThread.start();
        releaseThread.start();
    }

    public void stopThreads() {
        bookingThread.interrupt();
        releaseThread.interrupt();
    }
}
