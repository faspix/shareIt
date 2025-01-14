package com.shareit.item.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RequestItemDto {

    @Size(max = 255, message = "Name cannot be greater then 255 characters")
    @NotBlank(message = "Name shouldn't be blank")
    private final String name;

    @Size(max = 2000, message = "Description cannot be greater then 2000 characters")
    private final String description;

    @NotNull(message = "Available status shouldn't be null")
    private final Boolean available;


}
