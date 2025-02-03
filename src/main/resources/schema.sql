-- Create a new table
 drop table if exists user_role cascade;
  drop table if exists tasks cascade;
   drop table if exists users cascade;
    drop table if exists role cascade;
    
    
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    password VARCHAR(255)
);

-- Create the 'role' table
CREATE TABLE IF NOT EXISTS role (
    id SERIAL PRIMARY KEY,
    role VARCHAR(255) NOT NULL
);

-- Create the 'task' table
CREATE TABLE IF NOT EXISTS task  (
    id BIGSERIAL PRIMARY KEY,   -- task_id is a bigserial (auto-incrementing)
    creator_name VARCHAR(255),       -- creator_name column with a max length of 255 characters
    date DATE NOT NULL,              -- date column, cannot be NULL
    description VARCHAR(1200),       -- description column with a max length of 1200 characters
    is_completed BOOLEAN NOT NULL,   -- is_completed column, cannot be NULL
    name VARCHAR(255),               -- name column with a max length of 255 characters
    owner_id BIGINT,                -- owner_id as a BIGINT (since PostgreSQL does not have 'long')
   CONSTRAINT fk_owner_id FOREIGN KEY (owner_id) REFERENCES "users" (id)  -- Foreign key constraint

);

-- Create the 'user_role' table if it does not already exist
CREATE TABLE IF NOT EXISTS user_role (
    user_id BIGSERIAL NOT NULL,   -- user_id of type BIGINT, referencing the user table
    role_id SERIAL NOT NULL,      -- role_id of type INT, referencing the role table
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES "users" (id),  -- Foreign key to the 'user' table
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES role (id),    -- Foreign key to the 'role' table
    CONSTRAINT pk_user_role PRIMARY KEY (user_id, role_id)  -- Composite primary key to ensure unique user-role pairs
);

