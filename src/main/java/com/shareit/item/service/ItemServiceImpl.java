package com.shareit.item.service;

import com.shareit.item.dto.ItemDto;
import com.shareit.item.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public ItemDto addItem(Long userId, ItemDto itemDto) {
        return null;
    }

    @Override
    public ItemDto editItem(Long userId, Long itemId) {
        return null;
    }

    @Override
    public ItemDto getItem(Long userId, Long itemId) {
        return null;
    }

    @Override
    public Set<ItemDto> getAllUsersItems(Long userId) {
        return Set.of();
    }

    @Override
    public Set<ItemDto> findItems(String text) {
        return Set.of();
    }
}
