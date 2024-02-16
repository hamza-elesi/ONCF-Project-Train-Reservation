package src.test;


import src.voyageur.PDFTicketPrinter;

public class TestPDFTicketPrinter {

    public static void main(String[] args) {
        // Create an instance of PDFTicketPrinter
        PDFTicketPrinter printer = new PDFTicketPrinter();

        // Define ticket details
        String fromLocation = "New York";
        String toLocation = "Washington D.C.";
        String departureTime = "2023-10-05 08:00";
        String arrivalTime = "2023-10-05 11:00";
        String timeDifference = "3 hours";
        double price = 49.99;
        String filepath = "Ticket.pdf"; // Specify the path where you want the PDF to be saved

        // Print the ticket to a PDF file
        printer.printTicketToPDF(fromLocation, toLocation, departureTime, arrivalTime, timeDifference, price, filepath);

        System.out.println("Ticket printed to " + filepath);
    }
}
