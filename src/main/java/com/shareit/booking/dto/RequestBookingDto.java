package com.shareit.booking.dto;

import com.shareit.booking.utility.BookingStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestBookingDto {

    @Positive(message = "Item should be positive")
    private Long itemId;

    private LocalDate start;

    private LocalDate end;

}
