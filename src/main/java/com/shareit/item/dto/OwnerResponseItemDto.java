package com.shareit.item.dto;

import com.shareit.booking.dto.ResponseBookingDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor
@Builder
public class OwnerResponseItemDto {

    private final Long id;

    private final String name;

    private final String description;

    private final Boolean available;

    private ResponseBookingDto lastBooking;

    private ResponseBookingDto nextBooking;

}
