package com.shareit.booking.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
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

    @NotNull(message = "Start date shouldn't be null")
    @FutureOrPresent(message = "Start date should be in future")
    private LocalDate start;

    @NotNull(message = "End date shouldn't be null")
    @FutureOrPresent(message = "End date should be in future")
    private LocalDate end;

}
