package com.shareit.item.dto;

import com.shareit.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseCommentDto {

    private Long id;

    private User author;

    @NotBlank(message = "Text shouldn't be blank")
    private String text;

}
