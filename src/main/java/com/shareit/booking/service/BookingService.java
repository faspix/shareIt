package com.shareit.booking.service;

import com.shareit.booking.dto.RquestBookingDto;
import com.shareit.booking.dto.RequestBookingDto;
import com.shareit.booking.utility.BookingState;

import java.util.List;

public interface BookingService {

    RquestBookingDto createBooking(Long userId, RequestBookingDto savingDto);

    RquestBookingDto approveBooking(Long userId, Long bookingId, Boolean approvedStatus);

    RquestBookingDto getBooking(Long userId, Long bookingId);

    List<RquestBookingDto> getAllUserBookings(Long userId, BookingState state);

    List<RquestBookingDto> getOwnerBookings(Long userId, BookingState state);

}
