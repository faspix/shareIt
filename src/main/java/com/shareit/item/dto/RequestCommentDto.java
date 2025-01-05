package com.shareit.item.dto;

import com.shareit.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RequestCommentDto {

    @NotBlank(message = "Text shouldn't be blank")
    private String text;
//TODO: Max length
}