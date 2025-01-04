package com.shareit.exception;

public class BookingNotFoundException extends RuntimeException {
    public BookingNotFoundException(Long id) {
        super("Booking with ID " + id + " not found");
    }
}
