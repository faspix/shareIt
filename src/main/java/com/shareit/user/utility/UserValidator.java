package com.shareit.user.utility;

import com.shareit.exception.ValidationException;
import com.shareit.user.model.User;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class UserValidator {

    public static void validateUser(Long userId, Long ItemUserId) {
        if (!userId.equals(ItemUserId))
            throw new ValidationException("User ID " + userId + " does not own this item");
    }
}

