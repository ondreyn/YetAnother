package com.enterprise.yetanother.convertion.dtovalidation.implementations;

import com.enterprise.yetanother.dto.feedback.FeedbackDto;
import com.enterprise.yetanother.entities.Feedback;
import com.enterprise.yetanother.init.Properties;
import com.enterprise.yetanother.dto.feedback.FeedbackDto;
import com.enterprise.yetanother.entities.Feedback;
import com.enterprise.yetanother.init.Properties;
import com.enterprise.yetanother.convertion.dtovalidation.interfaces.FeedbackValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *@author andrey
 */
@Component
public class FeedbackValidatorImpl implements FeedbackValidator {

    final static Logger LOGGER = LoggerFactory
                                 .getLogger(FeedbackValidatorImpl.class);

    @Override
    public boolean validate(FeedbackDto feedbackDto) {

        if (feedbackDto == null) {
            LOGGER.warn("[validate: feedbackDto is null!]");
            return false;
        }

        String text = feedbackDto.getText();
        if (text != null) {
            if (!text.matches(Properties.TEXT_REGEXP)) {
                LOGGER.warn("[validate: text has errors!]");
                return false;
            }
        }

        Integer rate = feedbackDto.getRate();
        if (rate != null) {
            int more = rate.compareTo(0);
            int less = rate.compareTo(6);
            return more == 1 && less == -1;
        } else {
            return false;
        }
    }

    @Override
    public boolean validate(Feedback feedback) {
        if (feedback == null) {
            LOGGER.warn("[validate: feedback is null!]");
            return false;
        }

        String text = feedback.getText();
        Integer rate = feedback.getRate();

        if (rate == null) {
            LOGGER.warn("[validate: rate is null!]");
            return false;
        }

        if (text != null) {
            if (!text.matches(Properties.TEXT_REGEXP)) {
                LOGGER.warn("[validate: text has errors!]");
                return false;
            }
        }

        int more = rate.compareTo(0);
        int less = rate.compareTo(6);
        return more == 1 && less == -1;
    }
}
