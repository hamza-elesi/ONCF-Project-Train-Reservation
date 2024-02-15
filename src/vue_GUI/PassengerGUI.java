package src.vue_GUI;
import src.voyageur.TrainReservationGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PassengerGUI extends JFrame {
    private JButton reserveButton;
    private JLabel welcomeLabel;

    public PassengerGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("ONCF Train Reservation - Welcome");
        initUI();
        pack();
        setLocationRelativeTo(null);
    }

    private void initUI() {
        // Set the background to red
        getContentPane().setBackground(Color.RED);

        // Welcome label
        welcomeLabel = new JLabel("ONCF", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 36)); // Set a big and bold font for ONCF
        welcomeLabel.setForeground(Color.WHITE); // Set text color to white
        welcomeLabel.setPreferredSize(new Dimension(400, 100)); // Set preferred size for layout purposes

        // Reserve button
        reserveButton = new JButton("Reserve My Train");
        reserveButton.setFont(new Font("Arial", Font.BOLD, 18)); // Set a bold font for the button text
        reserveButton.setForeground(Color.BLACK);
        reserveButton.setBackground(Color.WHITE); // Set button background to white
        reserveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the Train Reservation GUI
                TrainReservationGUI trainReservationGUI = new TrainReservationGUI();
                trainReservationGUI.setVisible(true);
            }
        });

        // Layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(10, 0, 10, 0);

        add(welcomeLabel, gbc);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(reserveButton, gbc);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PassengerGUI().setVisible(true);
            }
        });
    }
}
