package com.shareit.booking.dto;

import com.shareit.booking.utility.BookingStatus;
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
public class RquestBookingDto {


    private Long id;

    private BookingStatus status;

    @NotBlank(message = "Item shouldn't be null")
    private Item item;

    @NotNull(message = "User shouldn't be null")
    private User booker;

    private LocalDate start;

    private LocalDate end;

}
