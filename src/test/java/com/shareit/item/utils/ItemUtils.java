package com.shareit.item.utils;

import com.shareit.item.dto.RequestCommentDto;
import com.shareit.item.dto.RequestItemDto;
import com.shareit.item.dto.ResponseCommentDto;
import com.shareit.item.dto.ResponseItemDto;
import com.shareit.item.model.Comment;
import com.shareit.item.model.Item;

import java.util.List;

import static com.shareit.user.utils.UserUtils.USER_TEST;

public class ItemUtils {

    public static final Long ITEM_ID_TEST = 1L;

    public static final Item ITEM_TEST = Item.builder()
            .id(ITEM_ID_TEST)
            .name("TestName")
            .owner(USER_TEST)
            .available(true)
            .description("TestDescription")
            .comments(List.of(new Comment(1L, USER_TEST, new Item(), "test")))
            .build();

    public static final ResponseItemDto RESPONSE_ITEM_DTO_TEST = ResponseItemDto.builder()
            .id(ITEM_TEST.getId())
            .name(ITEM_TEST.getName())
            .available(ITEM_TEST.getAvailable())
            .description(ITEM_TEST.getDescription())
            .comments(null)
            .build();

    public static final RequestItemDto REQUEST_ITEM_DTO_TEST = RequestItemDto.builder()
            .name(ITEM_TEST.getName())
            .available(ITEM_TEST.getAvailable())
            .description(ITEM_TEST.getDescription())
            .build();

    public static final Comment COMMENT_TEST = Comment.builder()
            .id(1L)
            .author(USER_TEST)
            .item(ITEM_TEST)
            .text("TestText")
            .build();

    public static final ResponseCommentDto RESPONSE_COMMENT_DTO_TEST = ResponseCommentDto.builder()
            .id(COMMENT_TEST.getId())
            .author(null)
            .text(COMMENT_TEST.getText())
            .build();

    public static final RequestCommentDto REQUEST_COMMENT_DTO_TEST = RequestCommentDto.builder()
            .text(COMMENT_TEST.getText())
            .build();


}
