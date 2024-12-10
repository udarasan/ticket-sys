package com.example.ticketsys.controller;

/**
 * @TimeStamp 2024-12-10 18:50
 * @ProjectDetails ticket sys
 * @Author udarasan
 */

import com.example.ticketsys.service.TicketService;
import com.example.ticketsys.util.ThreadManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final ThreadManager threadManager;

    public TicketController(TicketService ticketService, ThreadManager threadManager) {
        this.ticketService = ticketService;
        this.threadManager = threadManager;
    }

    @PostMapping("/add")
    public String addTickets(@RequestParam int count) {
        final StringBuilder builder = new StringBuilder();
        Runnable runnable=()->{
            synchronized (this){
                ticketService.addTickets(count);
                builder.append(count+" tickets added successfully!");
            }
        };
        Thread thread=new Thread(runnable);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            return "Error occurred while adding the ticket.";
        }

        return builder.toString();
    }

    @PostMapping("/book")
    public String bookTicket(@RequestParam int eventid,@RequestParam int ticketcount) {
        final StringBuilder result = new StringBuilder();

        Runnable runnable = () -> {
            synchronized (this) {  // Synchronize the booking operation
                boolean success = ticketService.bookTicket();
                result.append(success ? "Ticket booked successfully!" : "No tickets available.");
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

        try {
            // Wait for the thread to finish (optional but ensures thread completion before returning)
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Error occurred while booking the ticket.";
        }

        return result.toString(); // Return the result after thread execution
    }

    @GetMapping("/status")
    public long getAvailableTickets() {
        return ticketService.countAvailableTickets();
    }

    @PostMapping("/start")
    public String startThreads() {
        threadManager.startThreads();
        return "Ticket release and booking threads started!";
    }

    @PostMapping("/stop")
    public String stopThreads() {
        threadManager.stopThreads();
        return "Threads stopped.";
    }
}
