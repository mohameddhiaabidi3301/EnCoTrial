-- DB Creation
CREATE DATABASE IF NOT EXISTS eni_connect;
USE eni_connect;

-- Users Table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(120) NOT NULL,
    role VARCHAR(20),
    filiere VARCHAR(100),
    promotion VARCHAR(50),
    avatar_url VARCHAR(255),
    bio VARCHAR(500),
    is_active BIT DEFAULT 1,
    created_at DATETIME,
    updated_at DATETIME
);

-- Posts Table
CREATE TABLE IF NOT EXISTS posts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    content TEXT NOT NULL,
    image_url VARCHAR(255),
    author_id BIGINT NOT NULL,
    shares_count INT DEFAULT 0,
    created_at DATETIME,
    FOREIGN KEY (author_id) REFERENCES users(id)
);

-- Post Likes (Join Table)
CREATE TABLE IF NOT EXISTS post_likes (
    post_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (post_id, user_id),
    FOREIGN KEY (post_id) REFERENCES posts(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Comments Table
CREATE TABLE IF NOT EXISTS comments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    content TEXT NOT NULL,
    post_id BIGINT NOT NULL,
    author_id BIGINT NOT NULL,
    created_at DATETIME,
    FOREIGN KEY (post_id) REFERENCES posts(id),
    FOREIGN KEY (author_id) REFERENCES users(id)
);

-- Groups Table
CREATE TABLE IF NOT EXISTS groups_table (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    category VARCHAR(255),
    cover_url VARCHAR(255),
    creator_id BIGINT,
    posts_count INT DEFAULT 0,
    created_at DATETIME,
    FOREIGN KEY (creator_id) REFERENCES users(id)
);

-- Group Members (Join Table)
CREATE TABLE IF NOT EXISTS group_members (
    group_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (group_id, user_id),
    FOREIGN KEY (group_id) REFERENCES groups_table(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Events Table
CREATE TABLE IF NOT EXISTS events (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    event_date DATE,
    start_time TIME,
    end_time TIME,
    location VARCHAR(255),
    category VARCHAR(255),
    image_url VARCHAR(255),
    organizer_id BIGINT,
    organizer_name VARCHAR(255),
    created_at DATETIME,
    FOREIGN KEY (organizer_id) REFERENCES users(id)
);

-- Event Participants (Join Table)
CREATE TABLE IF NOT EXISTS event_participants (
    event_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (event_id, user_id),
    FOREIGN KEY (event_id) REFERENCES events(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Jobs Table
CREATE TABLE IF NOT EXISTS jobs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    company_name VARCHAR(255) NOT NULL,
    company_logo_url VARCHAR(255),
    description TEXT,
    job_type VARCHAR(50),
    location VARCHAR(255),
    salary VARCHAR(255),
    skills VARCHAR(255),
    posted_by BIGINT,
    is_active BIT DEFAULT 1,
    deadline DATETIME,
    created_at DATETIME,
    FOREIGN KEY (posted_by) REFERENCES users(id)
);

-- Messages Table
CREATE TABLE IF NOT EXISTS messages (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    content TEXT NOT NULL,
    sender_id BIGINT NOT NULL,
    receiver_id BIGINT,
    thread_id VARCHAR(255),
    thread_type VARCHAR(50),
    is_read BIT DEFAULT 0,
    created_at DATETIME,
    FOREIGN KEY (sender_id) REFERENCES users(id),
    FOREIGN KEY (receiver_id) REFERENCES users(id)
);
