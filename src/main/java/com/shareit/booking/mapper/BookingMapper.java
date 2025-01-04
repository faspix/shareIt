package com.shareit.booking.mapper;

import com.shareit.booking.dto.RquestBookingDto;
import com.shareit.booking.dto.RequestBookingDto;
import com.shareit.booking.model.Booking;

public class BookingMapper {


   public static Booking mapDtoToBooking(RquestBookingDto rquestBookingDto) {
       return Booking.builder()
               .booker(rquestBookingDto.getBooker())
               .item(rquestBookingDto.getItem())
               .start(rquestBookingDto.getStart())
               .end(rquestBookingDto.getEnd())
               .build();
   }


    public static RquestBookingDto mapBookingToDto(Booking booking) {
        return RquestBookingDto.builder()
                .booker(booking.getBooker())
                .item(booking.getItem())
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
