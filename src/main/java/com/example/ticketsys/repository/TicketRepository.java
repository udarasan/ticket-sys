package com.example.ticketsys.repository;

/**
 * @TimeStamp 2024-12-10 18:52
 * @ProjectDetails ticket sys
 * @Author udarasan
 */

import com.example.ticketsys.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findBySoldFalse();
}

