package com.enterprise.yetanother.convertion.dtovalidation.implementations;

import com.enterprise.yetanother.dao.interfaces.CategoryDao;
import com.enterprise.yetanother.dao.interfaces.TicketDao;
import com.enterprise.yetanother.dto.ticket.TicketDto;
import com.enterprise.yetanother.entities.Category;
import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.enums.State;
import com.enterprise.yetanother.enums.Urgency;
import com.enterprise.yetanother.init.Properties;
import com.enterprise.yetanother.convertion.dtovalidation.interfaces.EditTicketValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *@author andrey
 */
@Component
public class EditTicketValidatorImpl implements EditTicketValidator {

    final static Logger LOGGER = LoggerFactory
                                 .getLogger(EditTicketValidatorImpl.class);

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private TicketDao ticketDao;

    @Override
    public Ticket validate(TicketDto ticketDto, Long ticketId) {
        if (ticketDto == null) {
            LOGGER.warn("[validate: ticketDto ABSENT!]");
            return null;
        }

        if (ticketId == null) {
            LOGGER.warn("[validate: ticketId ABSENT!]");
            return null;
        }

        Ticket ticket = ticketDao.findTicketById(ticketId);
        if (ticket == null) {
            LOGGER.error("[validate: Ticket wasn't found!]");
            return null;
        }

        if (!ticket.getState().equals(State.DRAFT)) {
            LOGGER.error("[validate: Ticket is not in DRAFT status!]");
            return null;
        }

        State state = ticketDto.getState();
        try {
            if (!state.equals(State.NEW)) {
                LOGGER.warn("[validate: action is not NEW!]");
            } else {
                ticket.setState(state);
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            LOGGER.warn("[validate: Ticket State not set!]", e);
        }

        Category category = categoryDao.findByName(ticketDto
                                       .getCategory().getName());
        try {
            if (category != null) {
                ticket.setCategory(category);
            } else {
                LOGGER.warn("[validate: Ticket Category not set!]");
                return null;
            }
        } catch (Exception e) {
            LOGGER.error("[validate: Exception]", e);
            return null;
        }

        String ticketName = ticketDto.getName();
        try {
            if (ticketName.matches(Properties.NAME_REGEXP)) {
                ticket.setName(ticketName);
            } else {
                LOGGER.warn("[validate: Ticket Name not set!]");
                return null;
            }
        } catch (Exception e) {
            LOGGER.error("[validate: Exception!]", e);
            return null;
        }

        Urgency urgency = ticketDto.getUrgency();
        if (urgency != null) {
            ticket.setUrgency(urgency);
        } else {
            LOGGER.warn("[validate: Ticket Urgency not set!]");
            return null;
        }

        return ticket;
    }
}
