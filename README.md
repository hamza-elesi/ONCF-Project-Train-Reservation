# ONCF Project Train-Reservation application
Un système complet de réservation de train basé sur Java, conçu à la fois pour les passagers et les administrateurs
# Professeur Encadrant 
- **Sous la direction de Mr. EL MADANI EL ALAMI Yasser** 
# Train Reservation System

## Overview
This repository contains the Java code for a train reservation system. The system is designed to facilitate the booking of train tickets by travelers and to enable administrators to manage train schedules and discounts.

## Structure
The project is organized into several packages, reflecting the separation of concerns within the application:

- `Model`: Contains the core business logic and data models.
- `ONCF_APP`: Holds the `Main` class, which serves as the entry point of the application.
- `administrateur`: Provides the graphical user interface (GUI) components for administrator functionalities.
- `database`: Includes classes responsible for database connectivity and operations.
- `test`: Contains unit tests for the application.
- `voyageur`: Contains classes related to the traveler's functionalities, including ticket printing.
- `vue_GUI`: Contains GUI components for traveler interactions, such as login and ticket booking.

## How to Run
Make sure you have Java installed on your system. Clone the repository and navigate to the `src` directory. Compile the code using: javac ONCF_APP/Main.java 




## Features
- **User Authentication**: Secure login system for both travelers and administrators.
- **Train Management**: Administrators can add, update, and remove train schedules.
- **Ticket Booking**: Travelers can search for trains, choose seats, and book tickets.
- **Discount Management**: Administrators can manage discount cards and apply discount rules.
- **PDF Ticket Generation**: After booking, travelers can print their tickets in PDF format.

## Testing
The `test` package contains automated tests for validating the functionality of the application. Tests can be run using any Java testing framework compatible with JUnit.

## Dependencies
- `NB`: Make sure to import the jar of mysql-connector-j-8.2.0.jar to the project.
- `NB`: Make sure to import the jar of Itext.jar this for the PDF printer of the ticket.

## Contributors

- **[EL MAATAOUI Hamza](https://github.com/alicesmith)** 
- **[MOBHID Youssef](https://github.com/bobjohnson)**
- **[Anas HAMDOUNI](https://github.com/charlielee)**
- **[ESSAIDI Ziad ](https://github.com/dianareyes)**
- **[NAJIB Hamza](https://github.com/evanbrown)**
- **[ELMOUSSI Abdelbaste](https://github.com/fionachen)**

## License
This project is licensed under the [MIT License](LICENSE.md).

## Acknowledgments
- Mention any individuals or organizations that assisted in the development of this project.
- Any third-party assets or code included in the project.

We hope this system makes the train reservation process smoother and more accessible for all users.

Feel free to reach out to any of the team members for questions or suggestions regarding this project.

## Made With Love




