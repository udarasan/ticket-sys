package com.example.ticketsys.service;

/**
 * @TimeStamp 2024-12-10 18:53
 * @ProjectDetails ticket sys
 * @Author udarasan
 */

import com.example.ticketsys.model.Ticket;
import com.example.ticketsys.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final Object lock = new Object(); // Lock object for synchronization

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    // Add tickets with synchronized block to ensure thread safety
    public void addTickets(int count) {
        synchronized (lock) {
            for (int i = 0; i < count; i++) {
                Ticket ticket = new Ticket();
                ticketRepository.save(ticket);
            }
        }
    }

    // Book a ticket with synchronized block to ensure thread safety
    public boolean bookTicket() {
        synchronized (lock) {
            List<Ticket> availableTickets = ticketRepository.findBySoldFalse();

            if (availableTickets.isEmpty()) {
                return false;
            }

            // Book the first available ticket
            Ticket ticket = availableTickets.get(0);
            ticket.setSold(true);
            ticketRepository.save(ticket);
            return true;
        }
    }

    // Count available tickets
    public long countAvailableTickets() {
        return ticketRepository.findBySoldFalse().size();
    }
}

