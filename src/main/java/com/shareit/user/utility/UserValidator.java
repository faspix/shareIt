package com.shareit.user.utility;

import com.shareit.exception.ValidationException;

public class UserValidator {

    public static void validateUser(Long userId, Long ItemUserId) {
        if (!userId.equals(ItemUserId))
            throw new ValidationException("User ID " + userId + " does not own this item");
    }
}

