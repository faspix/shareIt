package com.shareit.booking.service;

import com.shareit.booking.dto.ResponseBookingDto;
import com.shareit.booking.dto.RequestBookingDto;
import com.shareit.booking.model.Booking;
import com.shareit.booking.utility.BookingState;
import com.shareit.user.model.User;

import java.util.List;

public interface BookingService {

    ResponseBookingDto createBooking(User user, RequestBookingDto savingDto);

    ResponseBookingDto approveBooking(User user, Long bookingId, Boolean approvedStatus);

    Booking getBooking(User user, Long bookingId);

    List<ResponseBookingDto> getAllUserBookings(User user, BookingState state, int page, int size);

    List<ResponseBookingDto> getOwnerBookings(User user, BookingState state, int page, int size);


}
