package com.enterprise.yetanother.convertion.dtovalidation.interfaces;

import com.enterprise.yetanother.dto.ticket.TicketDto;
import com.enterprise.yetanother.entities.Ticket;

/**
 *@author andrey
 */
public interface TicketValidator {

    boolean validate(Ticket ticket);
    boolean validate(TicketDto ticketDto);
}
