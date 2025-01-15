package com.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ResponseSearchItemDto {

    private final Long id;

    private final String name;

    private final String description;

}
