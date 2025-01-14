package com.shareit.item.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestCommentDto {

    @NotBlank(message = "Text shouldn't be blank")
    @Size(max = 255, message = "Text cannot be greater then 2000 characters")
    private String text;
}