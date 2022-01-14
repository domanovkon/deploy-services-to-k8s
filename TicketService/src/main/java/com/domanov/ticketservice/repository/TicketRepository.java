package com.domanov.ticketservice.repository;

import com.domanov.ticketservice.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    @Query("SELECT t FROM Ticket t WHERE t.username = ?1")
    List<Ticket> findByUsername(String usr);

    @Query("SELECT t FROM Ticket t WHERE t.ticket_uid = ?1")
    Ticket findByTicketUid(UUID ticketUid);

    @Query("SELECT t FROM Ticket t WHERE t.username = ?1 AND t.ticket_uid = ?2")
    Ticket findByUsernameAndTicketUid(String usr, UUID ticketUid);
}
