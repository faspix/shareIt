--liquibase formatted sql

--changeset faspix:1
CREATE TABLE IF NOT EXISTS Users (
    user_id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    role VARCHAR(20) DEFAULT 'USER',
    created_at TIMESTAMP,
    modified_at TIMESTAMP
);

--changeset faspix:2
CREATE TABLE IF NOT EXISTS Items (
    item_id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(2000),
    available BOOLEAN NOT NULL,
    owner_id BIGINT NOT NULL REFERENCES Users(user_id) ON DELETE CASCADE
);

--changeset faspix:3
CREATE TABLE IF NOT EXISTS Bookings (
    booking_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    status VARCHAR(50) NOT NULL CHECK (status IN ('WAITING', 'APPROVED', 'REJECTED')),
    item_id BIGINT NOT NULL REFERENCES Items(item_id) ON DELETE CASCADE ,
    booker_id BIGINT NOT NULL REFERENCES Users(user_id) ON DELETE CASCADE ,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL
);

--changeset faspix:4
CREATE TABLE IF NOT EXISTS Comments (
    comment_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    author_id BIGINT NOT NULL REFERENCES Users(user_id) ON DELETE CASCADE,
    item_id BIGINT NOT NULL REFERENCES Items(item_id) ON DELETE CASCADE,
    comment_text VARCHAR(2000) NOT NULL
);