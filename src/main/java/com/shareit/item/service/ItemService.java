package com.shareit.item.service;

import com.shareit.item.dto.*;
import com.shareit.item.model.Item;
import com.shareit.user.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ItemService {

    ResponseItemDtoNoComments addItem(User user, RequestItemDto item);

    ResponseItemDto editItem(User user, Long itemId, RequestItemDto itemDto);

    Item getItem(Long itemId);

    List<OwnerResponseItemDto> getAllUsersItems(User user, int page, int size);

    List<ResponseSearchItemDto> findItems(String query, int page, int size);

    ResponseEntity<HttpStatus> deleteItem(User user, Long itemId);

    ResponseCommentDto addComment(User user, Long itemId, RequestCommentDto commentDto);

}

