package com.enterprise.yetanother.convertion.dtoconverters.interfaces;

import com.enterprise.yetanother.dto.user.UserDto;
import com.enterprise.yetanother.entities.User;
import com.enterprise.yetanother.dto.user.UserDto;
import com.enterprise.yetanother.entities.User;

public interface UserDtoConverter {

    User dtoToEntity(UserDto userDto);
    UserDto entityToDto(User user);
}
