package com.enterprise.yetanother.controllers.tickets;

import com.enterprise.yetanother.dto.ticket.TicketDto;
import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.entities.User;
import com.enterprise.yetanother.services.interfaces.TicketService;
import com.enterprise.yetanother.services.interfaces.TicketsService;
import com.enterprise.yetanother.services.interfaces.UserService;
import com.enterprise.yetanother.convertion.dtoconverters.interfaces.TicketDtoConverter;
/*import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.entities.User;
import com.enterprise.yetanother.dto.ticket.TicketDto;
import com.enterprise.yetanother.services.interfaces.TicketService;
import com.enterprise.yetanother.services.interfaces.TicketsService;
import com.enterprise.yetanother.services.interfaces.UserService;*/
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *@author andrey
 */
@RestController
@RequestMapping(value = "/")
public class TicketsController {

    final static Logger LOGGER = LoggerFactory
                                 .getLogger(TicketsController.class);

    @Autowired
    private TicketsService ticketsService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    @Autowired
    private TicketDtoConverter ticketDtoConverter;

    @RequestMapping(value = "tickets", method = RequestMethod.GET,
                    produces = "application/json")
    public List<TicketDto> getAllAccessibleTickets() {

        try {
            String userLogin = userService.getUserLogin();
            User currentUser = userService.getUserByEmail(userLogin);
            List<Ticket> tickets = ticketsService
                                   .getAllAcceptableTickets(currentUser);

            if (tickets != null && !tickets.isEmpty()) {
                LOGGER.info("[getAllAccessibleTickets: " + tickets.size()
                            + " ticket/s]");
                return ticketDtoConverter.entitiesToDtos(tickets);
            } else {
                LOGGER.warn("[getAllAccessibleTickets: No tickets found]");
            }
        } catch (Exception e) {
            LOGGER.error("[getAllAccessibleTickets: Exception thrown!]", e);
        }
        return null;
    }

    @RequestMapping(value = "/tickets/{id}", method = RequestMethod.GET,
                    produces = "application/json")
    public TicketDto getTicketById(@PathVariable("id") Long id) {
        try {
            if (id != null && id != 0) {
                LOGGER.info("[getTicketById: Ticket " + id + " found]");
                return ticketDtoConverter.entityToDto(ticketService.findTicketById(id));
            } else {
                LOGGER.warn("[getTicketById: id is null or zero!]");
            }
        } catch (Exception e) {
            LOGGER.error("[getTicketById: Exception thrown!]", e);
        }
        return null;
    }
}
