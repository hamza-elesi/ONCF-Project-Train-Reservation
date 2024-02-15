package src.ONCF_APP;

import src.vue_GUI.LoginFrame;
import src.vue_GUI.PassengerGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create the main frame
            JFrame frame = new JFrame("ONCF Train Reservation System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

            // Button for admin login
            JButton adminLoginButton = new JButton("Admin Login");
            adminLoginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new LoginFrame().setVisible(true); // Open the LoginFrame for administrators
                }
            });

            // Button for passenger reservation
            JButton passengerButton = new JButton("Passenger Reservation");
            passengerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new PassengerGUI().setVisible(true); // Open the PassengerGUI for passengers
                }
            });

            // Add buttons to the frame
            frame.add(adminLoginButton);
            frame.add(passengerButton);

            // Pack the frame and make it visible
            frame.pack();
            frame.setLocationRelativeTo(null); // Center on screen
            frame.setVisible(true);
        });
    }
}
