-- Create a new database
CREATE DATABASE IF NOT EXISTS train;
USE train;

-- Create a new table called 'exampleTable'
CREATE TABLE IF NOT EXISTS exampleTable (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert some sample data into 'exampleTable'
INSERT INTO exampleTable (name, description) VALUES ('Sample Item 1', 'This is a description for item 1.');
INSERT INTO exampleTable (name, description) VALUES ('Sample Item 2', 'This is a description for item 2.');
INSERT INTO exampleTable (name, description) VALUES ('Sample Item 3', 'This is a description for item 3.');
