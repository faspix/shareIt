package com.shareit.item.mapper;

import com.shareit.item.dto.ResponseCommentDto;
import com.shareit.item.model.Comment;

import static com.shareit.user.mapper.UserMapper.mapUserToResponseUserDto;

public class CommentMapper {

    public static ResponseCommentDto mapCommentToResponseCommentDto(Comment comment) {
        return ResponseCommentDto.builder()
                .id(comment.getId())
                .author(mapUserToResponseUserDto(comment.getAuthor()))
                .text(comment.getText())
                .build();
    }

}
