package src.administrateur;

import src.database.DbConnector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

public class DiscountCardGUI extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;

    public DiscountCardGUI() {
        setTitle("Discount Card Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Card Type");
        tableModel.addColumn("Full Name");
        tableModel.addColumn("Email");
        tableModel.addColumn("Phone");

        table = new JTable(tableModel);
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

        loadDiscountCardData();
    }

    private void loadDiscountCardData() {
        try (Connection connection = DbConnector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM discount_card")) {

            while (resultSet.next()) {
                Vector<Object> row = new Vector<>();
                row.add(resultSet.getInt("id"));
                row.add(resultSet.getString("card_type"));
                row.add(resultSet.getString("full_name"));
                row.add(resultSet.getString("email"));
                row.add(resultSet.getString("phone"));
                tableModel.addRow(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addRow() {
        // Create a dialog for adding a new row
        JFrame addFrame = new JFrame("Add Discount Card");
        addFrame.setSize(500, 500);
        addFrame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add fields for each column
        JTextField idField = new JTextField(10); // Add ID field
        JTextField cardTypeField = new JTextField(10);
        JTextField fullNameField = new JTextField(10);
        JTextField emailField = new JTextField(10);
        JTextField phoneField = new JTextField(10);

        addFrame.add(new JLabel("ID:"), gbc);
        gbc.gridx++;
        addFrame.add(idField, gbc); // Add ID field
        gbc.gridx = 0;
        gbc.gridy++;
        addFrame.add(new JLabel("Card Type:"), gbc);
        gbc.gridx++;
        addFrame.add(cardTypeField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        addFrame.add(new JLabel("Full Name:"), gbc);
        gbc.gridx++;
        addFrame.add(fullNameField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        addFrame.add(new JLabel("Email:"), gbc);
        gbc.gridx++;
        addFrame.add(emailField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        addFrame.add(new JLabel("Phone:"), gbc);
        gbc.gridx++;
        addFrame.add(phoneField, gbc);

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
                newRow.add(idField.getText()); // Add ID field
                newRow.add(cardTypeField.getText());
                newRow.add(fullNameField.getText());
                newRow.add(emailField.getText());
                newRow.add(phoneField.getText());

                tableModel.addRow(newRow);

                // Add the new row to the database
                try (Connection connection = DbConnector.getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO discount_card (id, card_type, full_name, email, phone) VALUES (?, ?, ?, ?, ?)")) {

                    preparedStatement.setString(1, idField.getText()); // Add ID field
                    preparedStatement.setString(2, cardTypeField.getText());
                    preparedStatement.setString(3, fullNameField.getText());
                    preparedStatement.setString(4, emailField.getText());
                    preparedStatement.setString(5, phoneField.getText());

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
        JFrame modifyFrame = new JFrame("Modify Discount Card");
        modifyFrame.setSize(500, 500);
        modifyFrame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add fields for each column
        JTextField idField = new JTextField(10);
        JTextField cardTypeField = new JTextField(10);
        JTextField fullNameField = new JTextField(10);
        JTextField emailField = new JTextField(10);
        JTextField phoneField = new JTextField(10);

        modifyFrame.add(new JLabel("ID:"), gbc);
        gbc.gridx++;
        modifyFrame.add(idField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        modifyFrame.add(new JLabel("Card Type:"), gbc);
        gbc.gridx++;
        modifyFrame.add(cardTypeField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        modifyFrame.add(new JLabel("Full Name:"), gbc);
        gbc.gridx++;
        modifyFrame.add(fullNameField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        modifyFrame.add(new JLabel("Email:"), gbc);
        gbc.gridx++;
        modifyFrame.add(emailField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        modifyFrame.add(new JLabel("Phone:"), gbc);
        gbc.gridx++;
        modifyFrame.add(phoneField, gbc);

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
                        if (!cardTypeField.getText().isEmpty()) {
                            tableModel.setValueAt(cardTypeField.getText(), rowIndex, 1);
                        }
                        if (!fullNameField.getText().isEmpty()) {
                            tableModel.setValueAt(fullNameField.getText(), rowIndex, 2);
                        }
                        if (!emailField.getText().isEmpty()) {
                            tableModel.setValueAt(emailField.getText(), rowIndex, 3);
                        }
                        if (!phoneField.getText().isEmpty()) {
                            tableModel.setValueAt(phoneField.getText(), rowIndex, 4);
                        }

                        // Modify the row in the database
                        try (Connection connection = DbConnector.getConnection();
                             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE discount_card SET card_type=?, full_name=?, email=?, phone=? WHERE id=?")) {
                            preparedStatement.setString(1, cardTypeField.getText().isEmpty() ? tableModel.getValueAt(rowIndex, 1).toString() : cardTypeField.getText());
                            preparedStatement.setString(2, fullNameField.getText().isEmpty() ? tableModel.getValueAt(rowIndex, 2).toString() : fullNameField.getText());
                            preparedStatement.setString(3, emailField.getText().isEmpty() ? tableModel.getValueAt(rowIndex, 3).toString() : emailField.getText());
                            preparedStatement.setString(4, phoneField.getText().isEmpty() ? tableModel.getValueAt(rowIndex, 4).toString() : phoneField.getText());
                            preparedStatement.setString(5, idToModify);

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
        JFrame deleteFrame = new JFrame("Delete Discount Card");
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
                         PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM discount_card WHERE id = ?")) {
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
                new DiscountCardGUI().setVisible(true);
            }
   });
}
}