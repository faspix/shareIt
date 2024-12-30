package com.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ItemDto {

//    private final Long id;

    private final String name;

    private final String description;

    private final Boolean available;

//    private final Long ownerId;


}
