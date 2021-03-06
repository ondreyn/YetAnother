package com.enterprise.yetanother.convertion.dtovalidation.interfaces;

import com.enterprise.yetanother.dto.user.UserDto;
import com.enterprise.yetanother.entities.User;

/**
 *@author andrey
 */
public interface UserValidator {

    boolean validate(UserDto userDto);
    boolean validate(User user);
}
