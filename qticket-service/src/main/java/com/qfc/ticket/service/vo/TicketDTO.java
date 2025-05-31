package com.qfc.ticket.service.vo;

import com.qfc.ticket.service.dto.Ticket;

public class TicketDTO {
	private Long id;

    private Long userId;
    private String department;
    private String status;
    private String description;

    private Long lastUpdatedBy;

    private String statusHistory; // Could be a JSON string or comma-separated string

    private String comment;
    
    
    

	 public TicketDTO(Ticket updatedTicket) {
		// TODO Auto-generated constructor stub
	}

		public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getStatusHistory() {
		return statusHistory;
	}

	public void setStatusHistory(String statusHistory) {
		this.statusHistory = statusHistory;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

		// Getters and Setters
	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }


	    public String getDescription() {
	        return description;
	    }

	    public void setDescription(String description) {
	        this.description = description;
	    }

	    public String getDepartment() {
	        return department;
	    }

	    public void setDepartment(String department) {
	        this.department = department;
	    }

	    public String getStatus() {
	        return status;
	    }

	    public void setStatus(String status) {
	        this.status = status;
	    }

	    public Long getUserId() {
	        return userId;
	    }

	    public void setUserId(Long userId) {
	        this.userId = userId;
	    }
}
