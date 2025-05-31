package com.qfc.ticket.service.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qfc.ticket.service.dto.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long>{
	
	List<Ticket> findByCreatedBy(String username);

    List<Ticket> findByDepartment(String department);
}
