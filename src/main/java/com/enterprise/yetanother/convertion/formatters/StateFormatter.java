package com.enterprise.yetanother.convertion.formatters;

import com.enterprise.yetanother.enums.State;
import com.enterprise.yetanother.enums.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 *@author andrey
 */
@Component
public class StateFormatter implements Formatter<State> {

    final static Logger LOGGER = LoggerFactory.getLogger(StateFormatter.class);

    @Override
    public State parse(String s, Locale locale) {
        LOGGER.info("StateFormatter parse s: " + s);
        return State.valueOf(s);
    }

    @Override
    public String print(State state, Locale locale) {
        return state.toString();
    }
}
