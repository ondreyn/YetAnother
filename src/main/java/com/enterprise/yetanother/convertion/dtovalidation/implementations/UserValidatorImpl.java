package com.enterprise.yetanother.convertion.dtovalidation.implementations;

import com.enterprise.yetanother.dto.user.UserDto;
import com.enterprise.yetanother.entities.User;
import com.enterprise.yetanother.enums.Roles;
import com.enterprise.yetanother.init.Properties;
import com.enterprise.yetanother.entities.User;
import com.enterprise.yetanother.enums.Roles;
import com.enterprise.yetanother.init.Properties;
import com.enterprise.yetanother.dto.user.UserDto;
import com.enterprise.yetanother.convertion.dtovalidation.interfaces.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 *@author andrey
 */
@Component
public class UserValidatorImpl implements UserValidator {

    final static Logger LOGGER = LoggerFactory
                                 .getLogger(UserValidatorImpl.class);

    @Override
    public boolean validate(UserDto userDto) {
        if (userDto == null) {
            LOGGER.warn("[validate: userDto is null!]");
            return false;
        }

        try {
            Roles userRole = userDto.getRole();
            String userEmail = userDto.getEmail();
            List<Roles> roles = Arrays.asList(Roles.values());

            if (!roles.contains(userRole)) {
                LOGGER.warn("[validate: user has wrong role!]");
                return false;
            }

            if (!userEmail.matches(Properties.EMAIL_REGEXP)
                    || userEmail.length() > Properties.EMAIL_LENGTH) {
                LOGGER.warn("[validate: user email is wrong!]");
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("[validate: Exception thrown!]", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean validate(User user) {
        if (user != null) {
            try {
                String email = user.getEmail();
                Roles role = user.getRole();
                String password = user.getUserPassword();
                if (!email.matches(Properties.EMAIL_REGEXP)
                        || email.length() > Properties.EMAIL_LENGTH) {
                    LOGGER.warn("[validate: user email is wrong!]");
                    return false;
                }
                Roles[] roles = Roles.values();
                List<Roles> rolesList = Arrays.asList(roles);
                if (!rolesList.contains(role)) {
                    LOGGER.warn("[validate: user has wrong role!]");
                    return false;
                }

                if (!password.matches(Properties.PASSWORD_REGEXP)
                        || password.length() > Properties.PASSWORD_LENGTH_MAX
                        || password.length() < Properties.PASSWORD_LENGTH_MIN) {
                    LOGGER.warn("[validate: password non valid!]");
                    return false;
                }
                return true;
            } catch (Exception e) {
                LOGGER.error("[validate: Exception thrown!]", e);
            }
        } else {
            LOGGER.warn("[validate: user is null!]");
        }
        return false;
    }
}
