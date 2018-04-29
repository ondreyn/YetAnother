package com.enterprise.yetanother.convertion.dtovalidation.implementations;

import com.enterprise.yetanother.dto.ticket.TicketDto;
import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.enums.State;
import com.enterprise.yetanother.enums.Urgency;
import com.enterprise.yetanother.init.Properties;
import com.enterprise.yetanother.convertion.dtovalidation.interfaces.TicketValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 *@author andrey
 */
@Component
public class TicketValidatorImpl implements TicketValidator {

    final static Logger LOGGER = LoggerFactory
                                 .getLogger(TicketValidatorImpl.class);

    @Override
    public boolean validate(Ticket ticket) {
        if (ticket != null) {
            try {
                String category = ticket.getCategory().getName();
                String name = ticket.getName();
                Urgency urgency = ticket.getUrgency();
                State state = ticket.getState();
                String description = ticket.getDescription();

                if (!Properties.CATEGORY_NAMES.contains(category)) {
                    LOGGER.warn("[validate: wrong category!]");
                    return false;
                }
                if (!name.matches(Properties.NAME_REGEXP)) {
                    LOGGER.warn("[validate: wrong name!]");
                    return false;
                }

                State[] states = State.values();
                List<State> stateList = Arrays.asList(states);
                if (!stateList.contains(state)) {
                    LOGGER.warn("[validate: wrong state!]");
                    return false;
                }

                Urgency[] urgencies = Urgency.values();
                List<Urgency> urgencyList = Arrays.asList(urgencies);
                if (!urgencyList.contains(urgency)) {
                    LOGGER.warn("[validate: wrong urgency!]");
                    return false;
                }

                if (description != null) {
                    if (!description.matches(Properties.TEXT_REGEXP)) {
                        LOGGER.warn("[validate: wrong description!]");
                        return false;
                    }
                }
                return true;
            } catch (Exception e) {
                LOGGER.error("[validate: Exception thrown!]", e);
            }
        } else {
            LOGGER.warn("[validate: ticket is null!]");
        }
        return false;
    }

    @Override
    public boolean validate(TicketDto ticketDto) {
        if (ticketDto != null) {
            try {
                String category = ticketDto.getCategory().getName();
                String name = ticketDto.getName();
                Urgency urgency = ticketDto.getUrgency();
                State state = ticketDto.getState();
                String description = ticketDto.getDescription();

                if (!Properties.CATEGORY_NAMES.contains(category)) {
                    LOGGER.warn("[validate: wrong category!]");
                    return false;
                }
                if (!name.matches(Properties.NAME_REGEXP)) {
                    LOGGER.warn("[validate: wrong name!]");
                    return false;
                }

                State[] states = State.values();
                List<State> stateList = Arrays.asList(states);
                if (!stateList.contains(state)) {
                    LOGGER.warn("[validate: wrong state!]");
                    return false;
                }

                Urgency[] urgencies = Urgency.values();
                List<Urgency> urgencyList = Arrays.asList(urgencies);
                if (!urgencyList.contains(urgency)) {
                    LOGGER.warn("[validate: wrong urgency!]");
                    return false;
                }
                if (description != null) {
                    if (!description.matches(Properties.TEXT_REGEXP)) {
                        LOGGER.warn("[validate: wrong description!]");
                        return false;
                    }
                }
                LOGGER.info("[validate: success!]");
                return true;
            } catch (Exception e) {
                LOGGER.error("[validate: Exception thrown!]");
            }
        } else {
            LOGGER.warn("[validate: ticketDto is null!]");
        }
        return false;
    }
}
