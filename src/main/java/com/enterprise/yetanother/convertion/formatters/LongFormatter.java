package com.enterprise.yetanother.convertion.formatters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 *@author andrey
 */
@Component
public class LongFormatter implements Formatter<Long> {

    final static Logger LOGGER = LoggerFactory.getLogger(LongFormatter.class);

    @Override
    public Long parse(String s, Locale locale) {
        LOGGER.info("LongFormatter parse s: " + s);

        if (s.equalsIgnoreCase("{{attachment.id}}")) {
            return 0L;
        }

        try {
            return Long.valueOf(s);
        } catch (NumberFormatException e) {
            LOGGER.error("LongFormatter parse failed!", e);
            return 0L;
        }
    }

    @Override
    public String print(Long aLong, Locale locale) {
        try {
            return String.valueOf(aLong);
        } catch (Exception ex) {
            return "nothing parsed";
        }
    }
}
