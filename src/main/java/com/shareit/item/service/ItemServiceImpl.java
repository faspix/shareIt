package com.shareit.item.service;

import com.shareit.exception.ItemNotFoundException;
import com.shareit.exception.UserValidationException;
import com.shareit.item.dto.ItemDto;
import com.shareit.item.mapper.ItemMapper;
import com.shareit.item.model.Item;
import com.shareit.item.repository.ItemRepository;
import com.shareit.user.model.User;
import com.shareit.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.shareit.item.mapper.ItemMapper.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final UserService userService;

    public ItemServiceImpl(ItemRepository itemRepository, UserService userService) {
        this.itemRepository = itemRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public ItemDto addItem(Long userId, ItemDto itemDto) {
        User user = userService.getUser(userId);
        Item item = mapItemDtoToItem(itemDto);
        item.setOwner(user);
        return mapItemToItemDto(itemRepository.save(item));
    }

    @Override
    public ItemDto editItem(Long userId, Long itemId, ItemDto itemDto) {
        Item item = (itemRepository.findById(itemId).orElseThrow(
                () -> new ItemNotFoundException(itemId)
        ));

        validateUser(userId, item.getOwner().getId());

        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());

        return mapItemToItemDto(itemRepository.save(item));
    }

    @Override
    public ItemDto getItem(Long userId, Long itemId) {
        Item item = (itemRepository.findById(itemId).orElseThrow(
                () -> new ItemNotFoundException(itemId)
        ));
        validateUser(userId, item.getOwner().getId());
        return mapItemToItemDto(item);
    }

    @Override
    public List<ItemDto> getAllUsersItems(Long userId) {

        User user = userService.getUser(userId);

        return itemRepository.getAllByOwner(user).stream().toList();
    }

    @Override
    public List<ItemDto> findItems(String text) {



        return List.of();
    }


    private void validateUser(Long userId, Long ItemUserId) {
        if (userId != ItemUserId)
            throw new RuntimeException("user does not own this item");
    }



}

