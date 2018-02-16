package com.enterprise.yetanother.convertion.formatters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *@author andrey
 */
@Component
public class DateFormatter implements Formatter<Date> {

    final static Logger LOGGER = LoggerFactory.getLogger(DateFormatter.class);

    @Override
    public Date parse(String s, Locale locale) {
        LOGGER.info("[parse: " + s + "]");
        Calendar calendar = Calendar.getInstance();
        String[] results;

        try {
            results = s.split("-");
            calendar.set(Integer.valueOf(results[0]),
                    Integer.valueOf(results[1]) - 1,
                    Integer.valueOf(results[2]));
            return calendar.getTime();
        } catch (Exception e) {
            LOGGER.error("[parse: Exception thrown!]", e);
            return null;
        }
    }

    @Override
    public String print(Date date, Locale locale) {
        return date.toString();
    }
}
