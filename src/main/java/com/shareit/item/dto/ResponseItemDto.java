package com.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ResponseItemDto {

    private final Long id;

    private final String name;

    private final String description;

    private final Boolean available;

    private List<ResponseCommentDto> comments;

}
