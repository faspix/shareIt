package com.shareit.booking.mapper;

import com.shareit.booking.dto.ResponseBookingDto;
import com.shareit.booking.dto.RequestBookingDto;
import com.shareit.booking.model.Booking;

import static com.shareit.item.mapper.ItemMapper.*;
import static com.shareit.user.mapper.UserMapper.mapUserToResponseUserDto;

public class BookingMapper {

    public static ResponseBookingDto mapBookingToResponseBookingDto(Booking booking) {
        if (booking == null) return null;
        return ResponseBookingDto.builder()
                .booker(mapUserToResponseUserDto(booking.getBooker()))
                .item(mapItemToResponseItemDtoNoComments(booking.getItem()))
                .status(booking.getStatus())
                .id(booking.getId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .build();
    }

    public static Booking mapRequestBookingDtoToBooking(RequestBookingDto bookingDto) {
       return Booking.builder()
               .start(bookingDto.getStart())
               .end(bookingDto.getEnd())
               .build();
    }

}
