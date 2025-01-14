package com.shareit.user.dto;

import lombok.*;


@Data
@AllArgsConstructor
@Builder
public class ResponseUserDto {

    private Long id;

    private String name;

    private String email;


}

