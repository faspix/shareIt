package com.shareit.item.repository;

import com.shareit.item.dto.ResponseItemDto;
import com.shareit.item.model.Item;
import com.shareit.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {


    Set<ResponseItemDto> getAllByOwner(User owner);


    List<ResponseItemDto> findItemsByNameContainingIgnoreCase(String text);

    List<ResponseItemDto> findItemsByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String text, String text2);
}
