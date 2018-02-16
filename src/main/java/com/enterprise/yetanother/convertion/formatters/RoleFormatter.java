package com.enterprise.yetanother.convertion.formatters;

import com.enterprise.yetanother.enums.Roles;
import com.enterprise.yetanother.enums.Roles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 *@author andrey
 */
@Component
public class RoleFormatter implements Formatter<Roles> {

    final static Logger LOGGER = LoggerFactory.getLogger(RoleFormatter.class);

    @Override
    public Roles parse(String s, Locale locale) {
        LOGGER.info("RoleFormatter parse s: " + s);

        Roles[] enums = Roles.values();
        for (Roles role: enums) {
            if (s.contains(role.toString())) {
                return role;
            }
        }
        return null;
    }

    @Override
    public String print(Roles roles, Locale locale) {
        return roles.toString();
    }
}
