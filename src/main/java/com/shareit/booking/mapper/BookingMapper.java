package com.shareit.booking.mapper;

import com.shareit.booking.dto.ResponseBookingDto;
import com.shareit.booking.dto.RequestBookingDto;
import com.shareit.booking.model.Booking;
import com.shareit.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.shareit.item.mapper.ItemMapper.*;

@Component
@RequiredArgsConstructor
public class BookingMapper {

    private final UserMapper userMapper;

    public ResponseBookingDto mapBookingToResponseBookingDto(Booking booking) {
        if (booking == null) return null;
        return ResponseBookingDto.builder()
                .booker(userMapper.mapUserToResponseUserDto(booking.getBooker()))
                .item(mapItemToResponseItemDtoNoComments(booking.getItem()))
                .status(booking.getStatus())
                .id(booking.getId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .build();
    }

    public Booking mapRequestBookingDtoToBooking(RequestBookingDto bookingDto) {
       return Booking.builder()
               .start(bookingDto.getStart())
               .end(bookingDto.getEnd())
               .build();
    }

}
