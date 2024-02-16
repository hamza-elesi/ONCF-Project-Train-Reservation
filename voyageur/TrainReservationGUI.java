package src.voyageur;

import src.database.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class TrainReservationGUI extends JFrame {
    private JComboBox<String> fromLocationComboBox;
    private JComboBox<String> toLocationComboBox;
    private JButton searchButton, buyButton;
    private JTable resultsTable;
    private JRadioButton studentButton, enseignantButton, oncfManButton;
    private ButtonGroup discountGroup;

    public TrainReservationGUI() {
        initComponents();
        layoutComponents();
        initListeners();
        loadLocations();
        setupFrame();
    }

    private void initComponents() {
        fromLocationComboBox = new JComboBox<>();
        toLocationComboBox = new JComboBox<>();
        searchButton = new JButton("Search");
        searchButton.setBackground(new Color(255, 165, 0)); // Orange color for the button
        searchButton.setForeground(Color.WHITE);
        resultsTable = new JTable();
        studentButton = new JRadioButton("Student");
        enseignantButton = new JRadioButton("Enseignant");
        oncfManButton = new JRadioButton("ONCF Man");
        discountGroup = new ButtonGroup();
        discountGroup.add(studentButton);
        discountGroup.add(enseignantButton);
        discountGroup.add(oncfManButton);
        buyButton = new JButton("Buy Ticket");
        buyButton.setBackground(new Color(255, 140, 0)); // Darker orange for contrast
        buyButton.setForeground(Color.WHITE);
    }

    private void layoutComponents() {
        getContentPane().setLayout(new BorderLayout(5, 5));
        JPanel filtersPanel = new JPanel();
        filtersPanel.setLayout(new GridLayout(2, 2, 5, 5));
        filtersPanel.add(new JLabel("From:"));
        filtersPanel.add(fromLocationComboBox);
        filtersPanel.add(new JLabel("To:"));
        filtersPanel.add(toLocationComboBox);
        getContentPane().add(filtersPanel, BorderLayout.NORTH);

        JPanel resultsPanel = new JPanel(new BorderLayout());
        resultsPanel.setBorder(BorderFactory.createTitledBorder("Available Trains"));
        resultsPanel.add(new JScrollPane(resultsTable), BorderLayout.CENTER);
        getContentPane().add(resultsPanel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(searchButton);
        buttonsPanel.add(buyButton);
        getContentPane().add(buttonsPanel, BorderLayout.SOUTH);

        JPanel discountPanel = new JPanel();
        discountPanel.setLayout(new FlowLayout());
        discountPanel.add(new JLabel("Discount:"));
        discountPanel.add(studentButton);
        discountPanel.add(enseignantButton);
        discountPanel.add(oncfManButton);
        getContentPane().add(discountPanel, BorderLayout.EAST);
    }

    private void initListeners() {
        searchButton.addActionListener(this::searchButtonActionPerformed);
        buyButton.addActionListener(this::buyButtonActionPerformed);
    }

    private void searchButtonActionPerformed(ActionEvent evt) {
        String fromLocation = (String) fromLocationComboBox.getSelectedItem();
        String toLocation = (String) toLocationComboBox.getSelectedItem();
        searchTrajects(fromLocation, toLocation);
    }

    private void buyButtonActionPerformed(ActionEvent evt) {
        buyTicket();
    }

    private void loadLocations() {
        // This SQL aims to select all distinct locations from both from_location and to_location columns.
        String sql = "SELECT DISTINCT location FROM (" +
                "    SELECT from_location AS location FROM trajet " +
                "    UNION " +
                "    SELECT to_location AS location FROM trajet" +
                ") AS combinedLocations ORDER BY location ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // Assuming you want to clear the comboboxes first to avoid duplicate entries.
            fromLocationComboBox.removeAllItems();
            toLocationComboBox.removeAllItems();

            while (rs.next()) {
                String location = rs.getString("location");
                // Since the query returns a unified list of locations, we add each to both comboBoxes.
                fromLocationComboBox.addItem(location);
                toLocationComboBox.addItem(location);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading locations: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    private void setupFrame() {
        setTitle("Train Reservation System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
    }
    private void searchTrajects(String fromLocation, String toLocation) {
        Vector<String> columnNames = new Vector<>();
        columnNames.add("ID");
        columnNames.add("From");
        columnNames.add("To");
        columnNames.add("Departure Time");
        columnNames.add("Arrival Time");
        columnNames.add("Time Difference");
        columnNames.add("Price");

        Vector<Vector<Object>> data = new Vector<>();

        String sql = "SELECT * FROM trajet WHERE from_location = ? AND to_location = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, fromLocation);
            pstmt.setString(2, toLocation);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Vector<Object> vector = new Vector<>();
                vector.add(rs.getString("ID"));
                vector.add(rs.getString("from_location"));
                vector.add(rs.getString("to_location"));
                vector.add(rs.getTime("departure_time"));
                vector.add(rs.getTime("arrival_time"));
                vector.add(rs.getTime("time_difference"));
                vector.add(rs.getDouble("price"));
                data.add(vector);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        resultsTable.setModel(model);
    }

    private void buyTicket() {
        int selectedRow = resultsTable.getSelectedRow();
        if (selectedRow >= 0) {
            double price = (double) resultsTable.getValueAt(selectedRow, 6); // assuming price is in column 6
            String discountCardID = JOptionPane.showInputDialog(this, "Enter your discount card ID (if any):");

            // Assume no discount by default
            double discountRate = 0.0;
            double discountedPrice = price;
            String fromLocation = (String) resultsTable.getValueAt(selectedRow, 1);
            String toLocation = (String) resultsTable.getValueAt(selectedRow, 2);

            // If a discount card ID was entered, try to get the discount rate from the database
            if (discountCardID != null && !discountCardID.trim().isEmpty()) {
                String discountType = getDiscountTypeFromDB(discountCardID);
                discountRate = getDiscountRate(discountType);
                discountedPrice = price * (1 - discountRate);
            }

            // Show the discounted price to the user
            JOptionPane.showMessageDialog(this, "The price after discount is: " + discountedPrice);

            // Ask the user to enter the price they are paying
            String inputPrice = JOptionPane.showInputDialog(this, "Confirm the ticket price to pay:");
            try {
                double enteredPrice = Double.parseDouble(inputPrice);
                // Validate the entered price against the discounted price
                if (Math.abs(enteredPrice - discountedPrice) < 0.01) { // Check for approximate equality
                    JOptionPane.showMessageDialog(this, "Price matches. Ticket bought successfully.");

                    // Retrieve departure, arrival, and time difference from the table
                    String departureTimeString = resultsTable.getValueAt(selectedRow, 3).toString();
                    String arrivalTimeString = resultsTable.getValueAt(selectedRow, 4).toString();
                    String timeDifferenceString = resultsTable.getValueAt(selectedRow, 5).toString();

                    // Now, call the method to print the PDF ticket
                    PDFTicketPrinter pdfPrinter = new PDFTicketPrinter();
                    String filepath = "ticket.pdf"; // This will save in the project root directory
                    pdfPrinter.printTicketToPDF(
                            fromLocation, toLocation, departureTimeString, arrivalTimeString,
                            timeDifferenceString, discountedPrice, filepath
                    );


                } else {
                    JOptionPane.showMessageDialog(this, "Price does not match. Please enter the correct price.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Invalid price entered.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a train route before buying a ticket.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }
    private double getDiscountRate(String discountType) {
        if (discountType == null) {
            return 0.0; // No discount
        }
        switch (discountType) {
            case "Student":
                return 0.30; // 30% discount for students
            case "Enseignant":
                return 0.35; // 35% discount for enseignants
            case "ONCF man":
                return 0.40; // 40% discount for ONCF employees
            default:
                return 0.0; // No discount for unrecognized card types
        }
    }
    private String getDiscountTypeFromDB(String cardID) {
        String discountType = null;
        String sql = "SELECT card_type FROM discount_card WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cardID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                discountType = rs.getString("card_type");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid discount card ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return discountType;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TrainReservationGUI().setVisible(true);
            }
        });
    }
}
