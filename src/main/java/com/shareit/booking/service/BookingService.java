package com.shareit.booking.service;

import com.shareit.booking.dto.ResponseBookingDto;
import com.shareit.booking.dto.RequestBookingDto;
import com.shareit.booking.model.Booking;
import com.shareit.booking.utility.BookingState;
import com.shareit.user.model.User;

import java.util.List;

public interface BookingService {

    ResponseBookingDto createBooking(Long userId, RequestBookingDto savingDto);

    ResponseBookingDto approveBooking(Long userId, Long bookingId, Boolean approvedStatus);

    ResponseBookingDto getBooking(Long userId, Long bookingId);

    List<ResponseBookingDto> getAllUserBookings(Long userId, BookingState state);

    List<ResponseBookingDto> getOwnerBookings(Long userId, BookingState state);


}
