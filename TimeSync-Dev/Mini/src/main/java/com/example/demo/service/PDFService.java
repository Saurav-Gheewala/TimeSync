package com.example.demo.service;

import com.example.demo.controller.GenericController;
import jakarta.mail.MessagingException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class PDFService
{
    private final Logger logger = LoggerFactory.getLogger(PDFService.class);

    @Autowired
    TimetableService timetableService;
    @Autowired
    EmailService emailService;
    public ResponseEntity<String> createPDF(String email) throws IOException, MessagingException {
        try (PDDocument document = new PDDocument()) {
            List<String> timetable = timetableService.createTimetable();

            float margin = 40;
            float yStart = 695; // Initial Y position for text, adjusted for your layout
            float tableWidth = 500; // Example width, adjust as needed
            float xPosition = margin;

            // Define the row height
            float rowHeight = 20f;

            // Define the column width
            float columnWidth = tableWidth / 4;

            PDPage currentPage = new PDPage();
            document.addPage(currentPage);
            PDPageContentStream contentStream = new PDPageContentStream(document, currentPage);

            // Display each day's timetable vertically
            for (String entry : timetable) {
                String[] rowData = entry.split("-");
                if (rowData.length >= 3) { // Check if rowData has at least 3 elements
                    String day = rowData[0];
                    String slots = rowData[1];
                    drawCell(contentStream, xPosition, yStart, columnWidth, rowHeight, day);
                    drawCell(contentStream, xPosition + columnWidth, yStart, columnWidth * 2, rowHeight, slots); // Adjusted width for timing slots
                    for (int i = 2; i < rowData.length; i++) {
                        drawCell(contentStream, xPosition + columnWidth * (i + 1), yStart, columnWidth, rowHeight, rowData[i]); // Adjusted x-coordinate
                    }
                    yStart -= rowHeight;

                    // Check if the current page is full
                    if (yStart < margin) {
                        // Add a new page and reset yStart
                        contentStream.close(); // Close the current content stream
                        currentPage = new PDPage();
                        document.addPage(currentPage);
                        contentStream = new PDPageContentStream(document, currentPage);
                        yStart = 700; // Reset Y position for the new page

                    }
                }
            }

            if (contentStream != null) {
                contentStream.close();
            }

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.save(byteArrayOutputStream);
            byte[] generateTimetable = byteArrayOutputStream.toByteArray();
            logger.info("Generated");
            return emailService.sendMail(email, generateTimetable);
        }
    }
    private void drawTable(PDPageContentStream contentStream, float xStart, float yStart, float tableWidth,
                           float rowHeight, float cellMargin, String[] headers, float[] columnWidths) throws IOException {
        float yPosition = yStart;
        float tableHeight = rowHeight * 2; // 2 rows for header and one row for each entry

        // Draw the headers
        for (int i = 0; i < headers.length; i++) {
            float cellWidth = columnWidths[i] * tableWidth;
            drawCell(contentStream, xStart + i * cellWidth, yPosition, cellWidth, rowHeight, headers[i]);
            yPosition -= rowHeight;
        }
        contentStream.drawLine(xStart, yPosition + rowHeight, xStart + tableWidth, yPosition + rowHeight);

        // Draw the table border
        contentStream.drawLine(xStart, yStart, xStart + tableWidth, yStart); // Top border
        contentStream.drawLine(xStart, yStart - tableHeight, xStart + tableWidth, yStart - tableHeight); // Bottom border
        contentStream.drawLine(xStart, yStart, xStart, yStart - tableHeight); // Left border
        contentStream.drawLine(xStart + tableWidth, yStart, xStart + tableWidth, yStart - tableHeight); // Right border
    }

    private void drawCell(PDPageContentStream contentStream, float x, float y, float width, float height, String text) throws IOException {
        // Draw cell border
        contentStream.drawLine(x, y, x + width, y); // Top border
        contentStream.drawLine(x, y - height, x + width, y - height); // Bottom border
        contentStream.drawLine(x, y, x, y - height); // Left border
        contentStream.drawLine(x + width, y, x + width, y - height); // Right border

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 15);
        // Adjust text position to account for border
        contentStream.newLineAtOffset(x + 5, y - height + 5);

        // Split the text into course name, teacher, and timing slots
        String[] parts = text.split(" - ");

        if (parts.length >= 3) { // Check if there are at least 3 parts
            // Draw course name
            contentStream.showText(parts[0]);
            contentStream.newLineAtOffset(0, -height / 2);

            // Draw teacher
            contentStream.showText(" with " + parts[1]);
            contentStream.newLineAtOffset(0, -height / 2);

            // Draw timing slots
            contentStream.showText(" - " + parts[2]);
            contentStream.newLineAtOffset(0, -height / 2);
        } else {
            // If there are not enough parts, just draw the original text
            contentStream.showText(text);
        }

        contentStream.endText();
    }
}