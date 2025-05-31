package com.qfc.ticket.api;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qfc.ticket.service.AnalyticsService;
import com.qfc.ticket.service.ExcelExportService;
import com.qfc.ticket.service.dto.Ticket;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;
    private final ExcelExportService excelExportService;

    public AnalyticsController(AnalyticsService analyticsService, ExcelExportService excelExportService) {
        this.analyticsService = analyticsService;
        this.excelExportService = excelExportService;
    }

    @GetMapping("/tickets")
    public ResponseEntity<List<Ticket>> filterTickets(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String status) {
        return ResponseEntity.ok(analyticsService.filterTickets(startDate, endDate, department, status));
    }

    @GetMapping("/tickets/export")
    public ResponseEntity<byte[]> exportTicketsToExcel(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String status) {

        List<Ticket> filteredTickets = analyticsService.filterTickets(startDate, endDate, department, status);
        byte[] excelData = excelExportService.exportTicketsToExcel(filteredTickets);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=tickets.xlsx");
        return ResponseEntity.ok().headers(headers).body(excelData);
    }
}
