package com.enterprise.yetanother.convertion.dtovalidation.interfaces;

import com.enterprise.yetanother.dto.ticket.TicketDto;
import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.dto.ticket.TicketDto;

/**
 *@author andrey
 */
public interface EditTicketValidator {

    Ticket validate(TicketDto ticketDto, Long ticketId);
}
