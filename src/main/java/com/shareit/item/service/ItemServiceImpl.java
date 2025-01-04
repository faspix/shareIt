package com.shareit.item.service;

import com.shareit.exception.ItemNotFoundException;
import com.shareit.item.dto.RequestItemDto;
import com.shareit.item.dto.ResponseItemDto;
import com.shareit.item.model.Item;
import com.shareit.item.repository.ItemRepository;
import com.shareit.user.dto.ResponseUserDto;
import com.shareit.user.model.User;
import com.shareit.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.shareit.item.mapper.ItemMapper.*;
import static com.shareit.user.mapper.UserMapper.*;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final UserService userService;

    public ItemServiceImpl(ItemRepository itemRepository, UserService userService) {
        this.itemRepository = itemRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public ResponseItemDto addItem(Long userId, RequestItemDto itemDto) {
        User user = mapResponseUserDtoToUser(userService.getUser(userId));
        Item item = mapRequestItemDtoToItem(itemDto);
        item.setOwner(user);
        return mapItemToResponseItemDto(itemRepository.save(item));
    }

    @Override
    @Transactional
    public ResponseItemDto editItem(Long userId, Long itemId, RequestItemDto itemDto) {
        Item item = (itemRepository.findById(itemId).orElseThrow(
                () -> new ItemNotFoundException(itemId)
        ));

        validateUser(userId, item.getOwner().getId());

        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());

        return mapItemToResponseItemDto(itemRepository.save(item));
    }

    @Override
    public ResponseItemDto getItem(Long itemId) {
        Item item = (itemRepository.findById(itemId).orElseThrow(
                () -> new ItemNotFoundException(itemId)
        ));
        return mapItemToResponseItemDto(item);
    }

    @Override
    public List<ResponseItemDto> getAllUsersItems(Long userId) {
        User user = mapResponseUserDtoToUser(userService.getUser(userId));
        return itemRepository.getAllByOwner(user).stream().toList();
    }

    @Override
    public List<ResponseItemDto> findItems(String text) {
        List<ResponseItemDto> items = itemRepository.findItemsByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(text, text);
        return items;
    }

    @Override
    @Transactional
    public void deleteItem(Long userId, Long itemId) {
        Item item = (itemRepository.findById(itemId).orElseThrow(
                () -> new ItemNotFoundException(itemId)
        ));
        validateUser(userId, item.getOwner().getId());
        itemRepository.deleteById(itemId);
    }


    private void validateUser(Long userId, Long ItemUserId) {
        if (! userId.equals(ItemUserId))
            throw new RuntimeException("user does not own this item");
    }



}

