package com.shareit.user.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldNameConstants;


@Data
@AllArgsConstructor
@Builder
@FieldNameConstants
public class RequestUserDto {

    @NotBlank(message = "Name shouldn't be blank")
    @Size(max = 255, message = "Name cannot be greater then 255 characters")
    private String name;

    @NotBlank(message = "Email shouldn't be blank")
    @Email(message = "Email should be valid")
    @Size(max = 255, message = "Email cannot be greater then 255 characters")
    private String email;

}

