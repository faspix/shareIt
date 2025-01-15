package com.shareit.item.repository;

import com.shareit.item.dto.ResponseItemDto;
import com.shareit.item.model.Item;
import com.shareit.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    String searchItemSQL = "FROM Item i " +
            "WHERE i.available = TRUE " +
            "AND (LOWER(i.name) LIKE LOWER(CONCAT('%', :text, '%')) " +
            "OR LOWER(i.description) LIKE LOWER(CONCAT('%', :text, '%')))";
//    String searchItemSQL = "FROM Item i " +
//            "WHERE i.available = TRUE " +
//            "AND LOWER(i.name) LIKE LOWER(%:text%) " +
//            "OR LOWER(i.description) LIKE LOWER(%:text%)";
//
    @Query(searchItemSQL)
    Page<Item> searchItems(
        String text,
        Pageable pageRequest
    );

    Page<Item> getAllByOwner(User owner, Pageable pageRequest);

}
