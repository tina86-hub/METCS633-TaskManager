-- Truncate the 'user_role' table first (this is the child table)
TRUNCATE TABLE user_role CASCADE;

-- Truncate the 'task' table second (it depends on the 'users' table via owner_id)
TRUNCATE TABLE task CASCADE;

-- Truncate the 'users' table (parent table for 'task' and 'user_role')
TRUNCATE TABLE users CASCADE;

-- Truncate the 'role' table last (parent table for 'user_role')
TRUNCATE TABLE role CASCADE;


INSERT INTO role (role_id, role) VALUES (1, 'Admin'), (2, 'User');

-- Insert users into the 'users' table
INSERT INTO users (username, email, password) VALUES
('john_doe', 'john1.doe@example.com', 'password123'),
('jane_smith', 'jane1.smith@example.com', 'password456'),
('alex_brown', 'alex1.brown@example.com', 'password789'),
('emma_white', 'emma1.white@example.com', 'password101');

-- Insert tasks into the 'task' table
INSERT INTO task (creator_name, date, description, is_completed, name, owner_id) VALUES
('john_doe', '2025-01-28', 'Complete the monthly report and submit to the manager.', true, 'Report Submission', 1),
('jane_smith', '2025-01-28', 'Review the client feedback and prepare a response.', false, 'Client Feedback Review', 2),
('alex_brown', '2025-01-28', 'Design new UI elements for the website.', false, 'UI Design', 3),
('emma_white', '2025-01-28', 'Write content for the new product launch campaign.', true, 'Content Creation', 4);

-- Insert user-role relationships into the 'user_role' table
INSERT INTO user_role (user_id, role_id) VALUES
(1, 1),  -- john_doe -> Admin
(2, 2),  -- jane_smith -> User
(3, 1),  -- alex_brown -> Manager
(4, 1);