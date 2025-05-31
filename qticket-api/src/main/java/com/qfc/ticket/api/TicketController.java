package com.qfc.ticket.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qfc.ticket.service.TicketService;
import com.qfc.ticket.service.dto.Ticket;
import com.qfc.ticket.service.vo.TicketDTO;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<TicketDTO> updateTicket(@PathVariable Long id, @RequestBody TicketDTO ticketDTO) {
        Long userId = getCurrentUserId();

        Ticket ticket = new Ticket();
        ticket.setId(id);
        ticket.setDescription(ticketDTO.getDescription());
        ticket.setStatus(ticketDTO.getStatus());
        ticket.setComment(ticketDTO.getComment());

        // Call the service to update the ticket
        Ticket updatedTicket = ticketService.updateTicket(ticket, userId);

        if (updatedTicket == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new TicketDTO(updatedTicket));
    }

    @PatchMapping("/{id}/close")
    public ResponseEntity<TicketDTO> closeTicket(@PathVariable Long id) {
        Long userId = getCurrentUserId();

        Ticket closedTicket = ticketService.closeTicket(id, userId);

        if (closedTicket == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new TicketDTO(closedTicket));
    }

    @PatchMapping("/{id}/reassign")
    public ResponseEntity<TicketDTO> reassignTicket(@PathVariable Long id, @RequestParam Long newUserId) {
        Long userId = getCurrentUserId();

        Ticket reassignedTicket = ticketService.reassignTicket(id, newUserId, userId);

        if (reassignedTicket == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new TicketDTO(reassignedTicket));
    }

    
    @GetMapping("/user/{username}")
    public ResponseEntity<List<Ticket>> getTicketsByUser(@PathVariable String username) {
        return ResponseEntity.ok(ticketService.getTicketsByUser(username));
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<List<Ticket>> getTicketsByDepartment(@PathVariable String department) {
        return ResponseEntity.ok(ticketService.getTicketsByDepartment(department));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id, @RequestParam String username) {
        ticketService.deleteTicket(id, username);
        return ResponseEntity.noContent().build();
    }
    
    // Helper method to get the current logged-in user's ID
    private Long getCurrentUserId() {
        // Implement logic to extract the user ID from the session or JWT token
        return 1L;  // Placeholder: return the actual user ID from JWT or session
    }
}