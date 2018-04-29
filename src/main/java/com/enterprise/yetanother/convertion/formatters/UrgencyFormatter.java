package com.enterprise.yetanother.convertion.formatters;

import com.enterprise.yetanother.enums.Urgency;
//import com.enterprise.yetanother.enums.Urgency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 *@author andrey
 */
@Component
public class UrgencyFormatter implements Formatter<Urgency> {

    final static Logger LOGGER = LoggerFactory
                                 .getLogger(UrgencyFormatter.class);

    @Override
    public Urgency parse(String s, Locale locale) {
        LOGGER.info("UrgencyFormatter parse s: " + s);
        return Urgency.valueOf(s);
    }

    @Override
    public String print(Urgency urgency, Locale locale) {
        return urgency.toString();
    }
}
