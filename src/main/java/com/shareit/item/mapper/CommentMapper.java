package com.shareit.item.mapper;

import com.shareit.item.dto.ResponseCommentDto;
import com.shareit.item.model.Comment;

public class CommentMapper {

    public static ResponseCommentDto mapCommentToResponseCommentDto(Comment comment) {
        return ResponseCommentDto.builder()
                .id(comment.getId())
                .author(comment.getAuthor())
                .text(comment.getText())
                .build();
    }

}
