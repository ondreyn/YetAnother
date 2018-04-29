package com.enterprise.yetanother.convertion.dtovalidation.implementations;

import com.enterprise.yetanother.dto.comment.CommentDto;
import com.enterprise.yetanother.init.Properties;
import com.enterprise.yetanother.convertion.dtovalidation.interfaces.CommentValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 *@author andrey
 */
@Component
public class CommentValidatorImpl implements CommentValidator {

    final static Logger LOGGER = LoggerFactory
                                 .getLogger(CommentValidatorImpl.class);

    @Override
    public boolean validate(CommentDto commentDto) {

        if (commentDto == null) {
            LOGGER.warn("[validate: commentDto is null!]");
            return false;
        }

        try {
            if (commentDto.getText().matches(Properties.TEXT_REGEXP)) {
                return true;
            } else {
                LOGGER.warn("[validate: Comments input has errors" +
                            " or empty]");
                return false;
            }
        } catch (NullPointerException e) {
            LOGGER.error("[validate: NullPointerException]", e);
            return false;
        }
    }

    @Override
    public boolean validate(String text, Date date) {
        if (text == null) {
            LOGGER.warn("[validate: text is null!]");
            return false;
        }

        Date now = new Date();
        if (date == null || date.after(now)) {
            LOGGER.warn("[validate: date is null or after now!]");
            return false;
        }
        try {
            if (text.matches(Properties.TEXT_REGEXP)) {
                return true;
            } else {
                LOGGER.warn("[validate: Comment has errors or empty]");
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("[validate: Exception thrown!]", e);
            return false;
        }
    }
}
