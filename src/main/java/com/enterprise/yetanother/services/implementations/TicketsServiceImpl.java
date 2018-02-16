package com.enterprise.yetanother.services.implementations;

import com.enterprise.yetanother.dao.interfaces.TicketDao;
import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.entities.User;
import com.enterprise.yetanother.enums.Roles;
import com.enterprise.yetanother.services.interfaces.TicketsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *@author andrey
 */
@Service
public class TicketsServiceImpl implements TicketsService {

    final static Logger LOGGER = LoggerFactory
                                 .getLogger(TicketsServiceImpl.class);

    @Autowired
    private TicketDao ticketDao;

    @Override
    public List<Ticket> getAllAcceptableTickets(User user) {

        if (user != null) {
            if (user.getRole().equals(Roles.EMPLOYEE)) {
                return ticketDao.findForEmployeeById(user.getId());

            } else if (user.getRole().equals(Roles.MANAGER)) {
                return ticketDao.findForManagerById(user.getId());
            } else if (user.getRole().equals(Roles.ENGINEER)) {
                return ticketDao.findForEngineerById(user.getId());
            } else {
                LOGGER.warn("[getAllAcceptableTickets: nothing found]");
                return null;
            }
        } else {
            LOGGER.warn("[User is NULL]");
            return null;
        }
    }
}