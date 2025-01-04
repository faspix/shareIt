package com.shareit.user.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Data
@AllArgsConstructor
@Builder
public class RequestUserDto {

    @NotBlank(message = "Name shouldn't be blank")
    private String name;

    @NotBlank(message = "Email shouldn't be blank")
    @Email(message = "Email should be valid")
    private String email;


}

