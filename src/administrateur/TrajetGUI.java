package src.administrateur;

import src.database.DbConnector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class TrajetGUI extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;

    public TrajetGUI() {
        setTitle("Trajet Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("From");
        tableModel.addColumn("To");
        tableModel.addColumn("Departure Time");
        tableModel.addColumn("Arrival Time");
        tableModel.addColumn("Time Difference");
        tableModel.addColumn("Price");

        table = new JTable(tableModel);
        table.setBackground(new Color(255, 165, 0)); // Set the background color of the table to orange
        table.getTableHeader().setBackground(new Color(239, 46, 65)); // Set the background color of the table header
        JScrollPane scrollPane = new JScrollPane(table);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRow();
            }
        });

        JButton modifyButton = new JButton("Modify");
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifyRow();
            }
        });

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteRow();
            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Exit the application
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(exitButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Set the background color of the content pane
        getContentPane().setBackground(new Color(255, 165, 0)); // Orange

        loadTrajetData();
    }

    private void loadTrajetData() {
        try (Connection connection = DbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM trajet");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Vector<Object> row = new Vector<>();
                row.add(resultSet.getInt("ID"));
                row.add(resultSet.getString("from_location"));
                row.add(resultSet.getString("to_location"));
                row.add(resultSet.getTime("departure_time"));
                row.add(resultSet.getTime("arrival_time"));
                row.add(resultSet.getTime("time_difference"));
                row.add(resultSet.getDouble("price"));
                tableModel.addRow(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addRow() {
        // Create a dialog for adding a new row
        JFrame addFrame = new JFrame("Add Trajet");
        addFrame.setSize(500, 500);
        addFrame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add fields for each column
        JTextField idField = new JTextField(10);
        JTextField fromField = new JTextField(10);
        JTextField toField = new JTextField(10);
        JTextField departureField = new JTextField(10);
        JTextField arrivalField = new JTextField(10);
        JTextField differenceField = new JTextField(10);
        JTextField priceField = new JTextField(10);

        addFrame.add(new JLabel("ID:"), gbc);
        gbc.gridx++;
        addFrame.add(idField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        addFrame.add(new JLabel("From:"), gbc);
        gbc.gridx++;
        addFrame.add(fromField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        addFrame.add(new JLabel("To:"), gbc);
        gbc.gridx++;
        addFrame.add(toField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        addFrame.add(new JLabel("Departure Time:"), gbc);
        gbc.gridx++;
        addFrame.add(departureField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        addFrame.add(new JLabel("Arrival Time:"), gbc);
        gbc.gridx++;
        addFrame.add(arrivalField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        addFrame.add(new JLabel("Time Difference:"), gbc);
        gbc.gridx++;
        addFrame.add(differenceField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        addFrame.add(new JLabel("Price:"), gbc);
        gbc.gridx++;
        addFrame.add(priceField, gbc);

        JButton saveButton = new JButton("Save");
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        addFrame.add(saveButton, gbc);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add the new row to the table
                Vector<Object> newRow = new Vector<>();
                newRow.add(idField.getText());
                newRow.add(fromField.getText());
                newRow.add(toField.getText());
                newRow.add(departureField.getText());
                newRow.add(arrivalField.getText());
                newRow.add(differenceField.getText());
                newRow.add(priceField.getText());

                tableModel.addRow(newRow);

                // Add the new row to the database
                try (Connection connection = DbConnector.getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO trajet (ID, from_location, to_location, departure_time, arrival_time, time_difference, price) VALUES (?, ?, ?, ?, ?, ?, ?)")) {

                    preparedStatement.setString(1, idField.getText());
                    preparedStatement.setString(2, fromField.getText());
                    preparedStatement.setString(3, toField.getText());
                    preparedStatement.setString(4, departureField.getText());
                    preparedStatement.setString(5, arrivalField.getText());
                    preparedStatement.setString(6, differenceField.getText());
                    preparedStatement.setString(7, priceField.getText());

                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Row added to database successfully.");
                    } else {
                        System.out.println("Failed to add row to database.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                // Close the dialog
                addFrame.dispose();
            }
        });

        addFrame.setVisible(true);
    }

    private void modifyRow() {
        // Create a dialog for modifying a row
        JFrame modifyFrame = new JFrame("Modify Trajet");
        modifyFrame.setSize(500, 500);
        modifyFrame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add fields for each column
        JTextField idField = new JTextField(10);
        JTextField fromField = new JTextField(10);
        JTextField toField = new JTextField(10);
        JTextField departureField = new JTextField(10);
        JTextField arrivalField = new JTextField(10);
        JTextField differenceField = new JTextField(10);
        JTextField priceField = new JTextField(10);

        modifyFrame.add(new JLabel("ID:"), gbc);
        gbc.gridx++;
        modifyFrame.add(idField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        modifyFrame.add(new JLabel("From:"), gbc);
        gbc.gridx++;
        modifyFrame.add(fromField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        modifyFrame.add(new JLabel("To:"), gbc);
        gbc.gridx++;
        modifyFrame.add(toField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        modifyFrame.add(new JLabel("Departure Time:"), gbc);
        gbc.gridx++;
        modifyFrame.add(departureField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        modifyFrame.add(new JLabel("Arrival Time:"), gbc);
        gbc.gridx++;
        modifyFrame.add(arrivalField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        modifyFrame.add(new JLabel("Time Difference:"), gbc);
        gbc.gridx++;
        modifyFrame.add(differenceField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        modifyFrame.add(new JLabel("Price:"), gbc);
        gbc.gridx++;
        modifyFrame.add(priceField, gbc);

        JButton modifyConfirmButton = new JButton("Modify");
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        modifyFrame.add(modifyConfirmButton, gbc);

        modifyConfirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the ID to modify
                String idToModify = idField.getText();
                if (!idToModify.isEmpty()) {
                    // Modify the row in the table
                    int rowIndex = -1;
                    for (int i = 0; i < tableModel.getRowCount(); i++) {
                        if (tableModel.getValueAt(i, 0).toString().equals(idToModify)) {
                            rowIndex = i;
                            break;
                        }
                    }
                    if (rowIndex != -1) {
                        // Update the values in the table
                        if (!fromField.getText().isEmpty()) {
                            tableModel.setValueAt(fromField.getText(), rowIndex, 1);
                        }
                        if (!toField.getText().isEmpty()) {
                            tableModel.setValueAt(toField.getText(), rowIndex, 2);
                        }
                        if (!departureField.getText().isEmpty()) {
                            tableModel.setValueAt(departureField.getText(), rowIndex, 3);
                        }
                        if (!arrivalField.getText().isEmpty()) {
                            tableModel.setValueAt(arrivalField.getText(), rowIndex, 4);
                        }
                        if (!differenceField.getText().isEmpty()) {
                            tableModel.setValueAt(differenceField.getText(), rowIndex, 5);
                        }
                        if (!priceField.getText().isEmpty()) {
                            tableModel.setValueAt(priceField.getText(), rowIndex, 6);
                        }

                        // Modify the row in the database
                        try (Connection connection = DbConnector.getConnection();
                             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE trajet SET from_location=?, to_location=?, departure_time=?, arrival_time=?, time_difference=?, price=? WHERE ID=?")) {
                            preparedStatement.setString(7, idToModify);
                            // Update the values in the database
                            preparedStatement.setString(1, fromField.getText().isEmpty() ? tableModel.getValueAt(rowIndex, 1).toString() : fromField.getText());
                            preparedStatement.setString(2, toField.getText().isEmpty() ? tableModel.getValueAt(rowIndex, 2).toString() : toField.getText());
                            preparedStatement.setString(3, departureField.getText().isEmpty() ? tableModel.getValueAt(rowIndex, 3).toString() : departureField.getText());
                            preparedStatement.setString(4, arrivalField.getText().isEmpty() ? tableModel.getValueAt(rowIndex, 4).toString() : arrivalField.getText());
                            preparedStatement.setString(5, differenceField.getText().isEmpty() ? tableModel.getValueAt(rowIndex, 5).toString() : differenceField.getText());
                            preparedStatement.setString(6, priceField.getText().isEmpty() ? tableModel.getValueAt(rowIndex, 6).toString() : priceField.getText());

                            int rowsAffected = preparedStatement.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("Row modified in database successfully.");
                            } else {
                                System.out.println("No rows modified in database.");
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                // Close the dialog
                modifyFrame.dispose();
            }
        });

        modifyFrame.setVisible(true);
    }



    private void deleteRow() {
        // Create a dialog for deleting a row
        JFrame deleteFrame = new JFrame("Delete Trajet");
        deleteFrame.setSize(300, 100);
        deleteFrame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add a field for the ID of the row to delete
        JTextField idToDeleteField = new JTextField(10);

        deleteFrame.add(new JLabel("ID to delete:"), gbc);
        gbc.gridx++;
        deleteFrame.add(idToDeleteField, gbc);

        JButton deleteConfirmButton = new JButton("Delete");
        gbc.gridx++;
        deleteFrame.add(deleteConfirmButton, gbc);

        deleteConfirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the ID to delete
                String idToDelete = idToDeleteField.getText();
                if (!idToDelete.isEmpty()) {
                    // Delete the row from the table
                    int rowIndex = -1;
                    for (int i = 0; i < tableModel.getRowCount(); i++) {
                        if (tableModel.getValueAt(i, 0).toString().equals(idToDelete)) {
                            rowIndex = i;
                            break;
                        }
                    }
                    if (rowIndex != -1) {
                        tableModel.removeRow(rowIndex);
                    }

                    // Delete the row from the database
                    try (Connection connection = DbConnector.getConnection();
                         PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM trajet WHERE ID = ?")) {
                        preparedStatement.setString(1, idToDelete);
                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Row deleted from database successfully.");
                        } else {
                            System.out.println("No rows deleted from database.");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }

                // Close the dialog
                deleteFrame.dispose();
            }
        });

        deleteFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TrajetGUI().setVisible(true);
            }
        });
    }
}

