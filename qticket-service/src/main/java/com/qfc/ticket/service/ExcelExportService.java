package com.qfc.ticket.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.qfc.ticket.service.dto.Ticket;

@Service
public class ExcelExportService {

    public byte[] exportTicketsToExcel(List<Ticket> tickets) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Tickets");
            Row header = sheet.createRow(0);

            // Create headers
            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("Title");
            header.createCell(2).setCellValue("Description");
            header.createCell(3).setCellValue("Department");
            header.createCell(4).setCellValue("Status");
            header.createCell(5).setCellValue("Created At");
            header.createCell(6).setCellValue("Updated At");

            int rowIndex = 1;
            for (Ticket ticket : tickets) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(ticket.getId());
                row.createCell(2).setCellValue(ticket.getDescription());
                row.createCell(3).setCellValue(ticket.getDepartment());
                row.createCell(4).setCellValue(ticket.getStatus());
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to export data to Excel file");
        }
    }
}
