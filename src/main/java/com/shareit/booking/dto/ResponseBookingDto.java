package com.shareit.booking.dto;

import com.shareit.booking.utility.BookingStatus;
import com.shareit.item.dto.ResponseItemDtoNoComments;
import com.shareit.user.dto.ResponseUserDto;
import com.shareit.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseBookingDto {


    private Long id;

    private BookingStatus status;

    private ResponseItemDtoNoComments item;

    private ResponseUserDto booker;

    private LocalDate start;

    private LocalDate end;

}
