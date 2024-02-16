package src.voyageur;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class PDFTicketPrinter {

    public void printTicketToPDF(String fromLocation, String toLocation, String departureTime, String arrivalTime, String timeDifference, double price, String filepath) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filepath));
            document.open();
            document.add(new Paragraph("Train Ticket"));
            document.add(new Paragraph("From: " + fromLocation));
            document.add(new Paragraph("To: " + toLocation));
            document.add(new Paragraph("Departure Time: " + departureTime));
            document.add(new Paragraph("Arrival Time: " + arrivalTime));
            document.add(new Paragraph("Time Difference: " + timeDifference));
            document.add(new Paragraph("Price: " + String.format("%.2f", price)));
            document.close();
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
