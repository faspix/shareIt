package com.shareit.item.mapper;

import com.shareit.item.dto.ResponseCommentDto;
import com.shareit.item.model.Comment;
import com.shareit.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final UserMapper userMapper;

    public ResponseCommentDto mapCommentToResponseCommentDto(Comment comment) {
        return ResponseCommentDto.builder()
                .id(comment.getId())
                .author(userMapper.mapUserToResponseUserDto(comment.getAuthor()))
                .text(comment.getText())
                .build();
    }

}
