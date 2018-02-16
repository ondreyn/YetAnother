package com.enterprise.yetanother.convertion.dtoconverters.interfaces;

import com.enterprise.yetanother.dto.ticket.TicketDto;
import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.dto.ticket.TicketDto;

import java.util.List;

public interface TicketDtoConverter {

    Ticket dtoToEntity(TicketDto ticketDto);
    TicketDto entityToDto(Ticket ticket);
    List<TicketDto> entitiesToDtos(List<Ticket> tickets);
}
