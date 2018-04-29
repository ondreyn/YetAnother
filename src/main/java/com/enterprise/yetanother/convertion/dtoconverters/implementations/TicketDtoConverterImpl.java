package com.enterprise.yetanother.convertion.dtoconverters.implementations;

import com.enterprise.yetanother.convertion.dtovalidation.interfaces.TicketValidator;
import com.enterprise.yetanother.dto.ticket.TicketDto;
import com.enterprise.yetanother.entities.Category;
import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.services.interfaces.CategoryService;
import com.enterprise.yetanother.convertion.dtoconverters.interfaces.TicketDtoConverter;
/*import com.enterprise.yetanother.entities.Category;
import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.dto.ticket.TicketDto;
import com.enterprise.yetanother.services.interfaces.CategoryService;
import com.enterprise.yetanother.convertion.dtovalidation.interfaces.TicketValidator;*/
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *@author andrey
 */
@Component
public class TicketDtoConverterImpl implements TicketDtoConverter {

    final static Logger LOGGER = LoggerFactory
                                 .getLogger(TicketDtoConverterImpl.class);

    @Autowired
    private TicketValidator ticketValidator;

    @Autowired
    private CategoryService categoryService;

    @Override
    public Ticket dtoToEntity(TicketDto ticketDto) {

        if (ticketValidator.validate(ticketDto)) {
            Ticket ticket = new Ticket();
            ticket.setCreatedOn(new Date());
            ticket.setDescription(ticketDto.getDescription());
            ticket.setState(ticketDto.getState());

            Category category = categoryService.getCategory(ticketDto
                                               .getCategory().getName());
            ticket.setCategory(category);
            ticket.setName(ticketDto.getName());
            ticket.setUrgency(ticketDto.getUrgency());

            Date desiredDate = ticketDto.getDesiredResolutionDate();
            if (desiredDate != null) {
                ticket.setDesiredResolutionDate(desiredDate);
            }
            return ticket;
        } else {
            LOGGER.warn("[dtoToEntity: ticketDto dtovalidation failed!]");
        }
        return null;
    }

    @Override
    public TicketDto entityToDto(Ticket ticket) {
        if (ticketValidator.validate(ticket)) {
            TicketDto ticketDto = new TicketDto();
            ticketDto.setId(ticket.getId());
            ticketDto.setDesiredResolutionDate(ticket
                                                 .getDesiredResolutionDate());
            ticketDto.setCreatedOn(ticket.getCreatedOn());
            ticketDto.setDescription(ticket.getDescription());
            ticketDto.setState(ticket.getState());
            ticketDto.setCategory(ticket.getCategory());
            ticketDto.setName(ticket.getName());
            ticketDto.setUrgency(ticket.getUrgency());
            ticketDto.setApprover(ticket.getApprover());
            ticketDto.setOwner(ticket.getOwner());
            ticketDto.setAssignee(ticket.getAssignee());
            ticketDto.setFeedback(ticket.getFeedback());
            return ticketDto;
        } else {
            LOGGER.warn("[entityToDto: dtovalidation failed!]");
        }
        return null;
    }

    @Override
    public List<TicketDto> entitiesToDtos(List<Ticket> tickets) {
        if (tickets != null) {
            try {
                List<TicketDto> ticketDtos = new ArrayList<>();
                for (Ticket ticket: tickets) {
                    ticketDtos.add(entityToDto(ticket));
                }
                return ticketDtos;
            } catch (Exception e) {
                LOGGER.error("[entitiesToDtos: Exception thrown!]", e);
            }
        } else {
            LOGGER.warn("[entitiesToDtos: tickets are null!]");
        }
        return null;
    }
}
