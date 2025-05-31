package com.qfc.ticket.service;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.qfc.ticket.service.dao.TicketRepository;
import com.qfc.ticket.service.dto.Ticket;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public void deleteTicket(Long id, String username) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        if (!ticket.getCreatedBy().equals(username)) {
            throw new RuntimeException("You can only delete your own tickets.");
        }

        ticketRepository.delete(ticket);
    }
    
    public Ticket createTicket(Ticket ticket, String username) {
        ticket.setCreatedBy(username);
        ticket.setStatus("OPEN"); // Default status
        return ticketRepository.save(ticket);
    }
    
    
    public Ticket updateTicket(Ticket ticket, Long userId) {
        // Fetch the ticket to be updated
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticket.getId());
        if (!ticketOptional.isPresent()) {
            return null;  // Return null if ticket does not exist
        }

        Ticket existingTicket = ticketOptional.get();

        // Update ticket fields
        existingTicket.setDescription(ticket.getDescription());
        existingTicket.setStatus(ticket.getStatus());
        existingTicket.setLastUpdatedBy(userId);

        // Add the status to history
        String updatedStatusHistory = existingTicket.getStatusHistory() == null ? ticket.getStatus() 
            : existingTicket.getStatusHistory() + "," + ticket.getStatus();
        existingTicket.setStatusHistory(updatedStatusHistory);

        // Optionally, you can also store comments for each action (update/close/reassign)
        existingTicket.setComment(ticket.getComment());

        // Save the updated ticket
        return ticketRepository.save(existingTicket);
    }

    public Ticket closeTicket(Long ticketId, Long userId) {
        // Fetch the ticket to be closed
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);
        if (!ticketOptional.isPresent()) {
            return null;  // Return null if ticket does not exist
        }

        Ticket ticket = ticketOptional.get();
        ticket.setStatus("Closed");
        ticket.setLastUpdatedBy(userId);

        // Add the status to history
        String updatedStatusHistory = ticket.getStatusHistory() == null ? "Closed" 
            : ticket.getStatusHistory() + ",Closed";
        ticket.setStatusHistory(updatedStatusHistory);

        // Optionally, store a comment for closure
        ticket.setComment("Ticket closed by user.");

        // Save the updated ticket
        return ticketRepository.save(ticket);
    }

    public Ticket reassignTicket(Long ticketId, Long newUserId, Long userId) {
        // Fetch the ticket to be reassigned
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);
        if (!ticketOptional.isPresent()) {
            return null;  // Return null if ticket does not exist
        }

        Ticket ticket = ticketOptional.get();
        ticket.setUserId(newUserId);
        ticket.setLastUpdatedBy(userId);

        // Add the status to history
        String updatedStatusHistory = ticket.getStatusHistory() == null ? ticket.getStatus() 
            : ticket.getStatusHistory() + "," + ticket.getStatus();
        ticket.setStatusHistory(updatedStatusHistory);

        // Optionally, store a comment for reassignment
        ticket.setComment("Ticket reassigned.");

        // Save the updated ticket
        return ticketRepository.save(ticket);
    }
    
    public List<Ticket> getTicketsByUser(String username) {
        return ticketRepository.findByCreatedBy(username);
    }

    public List<Ticket> getTicketsByDepartment(String department) {
        return ticketRepository.findByDepartment(department);
    }
}