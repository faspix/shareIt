package com.shareit.item.dto;

import com.shareit.user.dto.ResponseUserDto;
import com.shareit.user.model.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseCommentDto {

    private Long id;

    private ResponseUserDto author;

    private String text;

}
