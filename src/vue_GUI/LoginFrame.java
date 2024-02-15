package src.vue_GUI;

import src.administrateur.DiscountCardGUI;
import src.administrateur.TrajetGUI;
import src.database.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFrame extends JFrame implements ActionListener {
    private JTextField userTextField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton trajetButton;
    private JButton discountCardButton;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JPanel mainPanel;

    public LoginFrame() {
        initializeFrame();
        initializeComponents();
        layoutComponents();
        addActionListeners();
    }

    private void initializeFrame() {
        setTitle("ONCF Train Reservation System - Admin Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.ORANGE);
        setSize(300, 200);
        setLocationRelativeTo(null);
    }

    private void initializeComponents() {
        userTextField = new JTextField(15);
        passwordField = new JPasswordField(15);
        loginButton = new JButton("Login");
        trajetButton = new JButton("Trajet");
        discountCardButton = new JButton("Discount Card");
        usernameLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(Color.ORANGE);
    }

    private void layoutComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        mainPanel.add(usernameLabel, gbc);
        gbc.gridy++;
        mainPanel.add(userTextField, gbc);
        gbc.gridy++;
        mainPanel.add(passwordLabel, gbc);
        gbc.gridy++;
        mainPanel.add(passwordField, gbc);
        gbc.gridy++;

        loginButton.setBackground(Color.GREEN);
        mainPanel.add(loginButton, gbc);

        trajetButton.setBackground(Color.GREEN);
        discountCardButton.setBackground(Color.GREEN);

        gbc.gridy++;
        trajetButton.setVisible(false);
        mainPanel.add(trajetButton, gbc);
        gbc.gridy++;
        discountCardButton.setVisible(false);
        mainPanel.add(discountCardButton, gbc);

        this.add(mainPanel);
    }

    private void addActionListeners() {
        loginButton.addActionListener(this);
        trajetButton.addActionListener(e -> new TrajetGUI().setVisible(true));
        discountCardButton.addActionListener(e -> new DiscountCardGUI().setVisible(true));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = userTextField.getText();
        String password = new String(passwordField.getPassword()); // Hash the password in production

        if (checkLogin(username, password)) {
            JOptionPane.showMessageDialog(this, "Login successful!");

            userTextField.setVisible(false);
            passwordField.setVisible(false);
            loginButton.setVisible(false);
            usernameLabel.setVisible(false);
            passwordLabel.setVisible(false);

            trajetButton.setVisible(true);
            discountCardButton.setVisible(true);

            this.pack();
            this.setLocationRelativeTo(null);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean checkLogin(String username, String password) {
        final String QUERY = "SELECT * FROM admin WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(QUERY)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password); // Hash the password in production

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
