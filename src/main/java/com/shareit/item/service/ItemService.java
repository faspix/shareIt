package com.shareit.item.service;

import com.shareit.item.dto.*;
import com.shareit.item.model.Item;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ItemService {

    ResponseItemDtoNoComments addItem(Long userId, RequestItemDto item);

    ResponseItemDto editItem(Long userId, Long itemId, RequestItemDto itemDto);

    Item getItem(Long itemId);

    List<OwnerResponseItemDto> getAllUsersItems(Long userId, int page, int size);

    List<ResponseSearchItemDto> findItems(String text, int page, int size);

    ResponseEntity<HttpStatus> deleteItem(Long userId, Long itemId);

    ResponseCommentDto addComment(Long userId, Long itemId, RequestCommentDto commentDto);

}

