package com.shareit.item.dto;

import com.shareit.booking.dto.ResponseBookingDto;
import com.shareit.booking.model.Booking;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class OwnerResponseItemDto {

    private final Long id;

    @NotBlank(message = "Name shouldn't be blank")
    private final String name;

    private final String description;

    @NotNull(message = "Available status shouldn't be null")
    private final Boolean available;

    private ResponseBookingDto lastBooking;

    private ResponseBookingDto nextBooking;

}
