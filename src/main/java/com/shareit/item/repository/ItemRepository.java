package com.shareit.item.repository;

import com.shareit.item.dto.ItemDto;
import com.shareit.item.model.Item;
import com.shareit.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

//@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {


    Set<ItemDto> getAllByOwner(User owner);
}
