package com.shareit.booking.dto;

import com.shareit.booking.utility.BookingStatus;
import com.shareit.item.dto.ResponseItemDto;
import com.shareit.item.dto.ResponseItemDtoNoComments;
import com.shareit.item.model.Item;
import com.shareit.user.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "Item shouldn't be null")
    private ResponseItemDtoNoComments item;

    @NotNull(message = "User shouldn't be null")
    private User booker;

    private LocalDate start;

    private LocalDate end;

}
