package com.enterprise.yetanother.convertion.dtoconverters.implementations;

import com.enterprise.yetanother.convertion.dtovalidation.interfaces.UserValidator;
import com.enterprise.yetanother.dto.user.UserDto;
import com.enterprise.yetanother.entities.User;
import com.enterprise.yetanother.enums.Roles;
import com.enterprise.yetanother.services.interfaces.UserService;
import com.enterprise.yetanother.convertion.dtoconverters.interfaces.UserDtoConverter;
/*import com.enterprise.yetanother.entities.User;
import com.enterprise.yetanother.enums.Roles;
import com.enterprise.yetanother.dto.user.UserDto;
import com.enterprise.yetanother.services.interfaces.UserService;
import com.enterprise.yetanother.convertion.dtovalidation.interfaces.UserValidator;*/
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *@author andrey
 */
@Component
public class UserDtoConverterImpl implements UserDtoConverter {

    final static Logger LOGGER = LoggerFactory
                                 .getLogger(UserDtoConverterImpl.class);

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserService userService;

    @Override
    public User dtoToEntity(UserDto userDto) {

        if (userValidator.validate(userDto)) {
            try {
                User user = userService.getUserByEmail(userDto.getEmail());
                Roles modelRole = userDto.getRole();
                Roles userRole = user.getRole();
                if (modelRole == userRole) {
                    LOGGER.info("[dtoToEntity: successful!]");
                    return user;
                } else {
                    LOGGER.warn("[dtoToEntity: roles' mismatch!]");
                }
            } catch (Exception e) {
                LOGGER.error("[dtoToEntity: Exception thrown!]", e);
            }
        } else {
            LOGGER.warn("[dtoToEntity: dtovalidation failed!]");
        }
        return null;
    }

    @Override
    public UserDto entityToDto(User user) {

        if (userValidator.validate(user)) {
            try {
                UserDto userDto = new UserDto();
                userDto.setAddress(user.getAddress());
                userDto.setId(user.getId());
                userDto.setPhone(user.getPhone());
                userDto.setPosition(user.getPosition());
                userDto.setFirstName(user.getFirstName());
                userDto.setLastName(user.getLastName());
                userDto.setEmail(user.getEmail());
                userDto.setRole(user.getRole());
                return userDto;
            } catch (Exception e) {
                LOGGER.warn("[entityToDto: Exception thrown!]");
                return null;
            }
        } else {
            LOGGER.warn("[entityToDto: dtovalidation failed!]");
            return null;
        }
    }
}
