package com.shareit.item.dto;

import com.shareit.item.model.Comment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ResponseItemDtoNoComments {

    private final Long id;

    @NotBlank(message = "Name shouldn't be blank")
    private final String name;

    private final String description;

    @NotNull(message = "Available status shouldn't be null")
    private final Boolean available;

}
