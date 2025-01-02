package com.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Data
@AllArgsConstructor
@Builder
public class UserDto {

//    private final Long id;

    @NotBlank(message = "Name shouldn't be blank")
    private String name;


    @Email(message = "Email should be valid")
    @NotBlank(message = "Email shouldn't be blank")
    private String email;


}

