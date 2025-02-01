package com.shareit.booking.utils;

import com.shareit.booking.dto.RequestBookingDto;
import com.shareit.booking.dto.ResponseBookingDto;
import com.shareit.booking.model.Booking;
import com.shareit.booking.utility.BookingStatus;
import com.shareit.item.dto.ResponseItemDtoNoComments;

import java.time.LocalDate;

import static com.shareit.item.utils.ItemUtils.ITEM_ID_TEST;
import static com.shareit.item.utils.ItemUtils.ITEM_TEST;
import static com.shareit.user.utils.UserUtils.RESPONSE_USER_DTO_TEST;
import static com.shareit.user.utils.UserUtils.USER_TEST;

public class BookingUtils {

    public static final Long BOOKING_ID_TEST = 1L;

    public static final Booking BOOKING_TEST = Booking.builder()
            .id(BOOKING_ID_TEST)
            .status(BookingStatus.APPROVED)
            .start(LocalDate.now())
            .end(LocalDate.now())
            .booker(USER_TEST)
            .item(ITEM_TEST)
            .build();

    public static final ResponseBookingDto RESPONSE_BOOKING_DTO_TEST = ResponseBookingDto.builder()
            .id(BOOKING_TEST.getId())
            .booker(RESPONSE_USER_DTO_TEST)
            .status(BOOKING_TEST.getStatus())
            .item(new ResponseItemDtoNoComments(1L, "Name", "desc", true))
            .start(BOOKING_TEST.getStart())
            .end(BOOKING_TEST.getEnd())
            .build();

    public static final RequestBookingDto REQUEST_BOOKING_DTO_TEST = RequestBookingDto.builder()
            .itemId(ITEM_ID_TEST)
            .start(BOOKING_TEST.getStart())
            .end(BOOKING_TEST.getEnd())
            .build();

}
