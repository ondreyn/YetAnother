package com.enterprise.yetanother.services.interfaces;

import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *@author andrey
 */
@Service
public interface TicketsService {

    List<Ticket> getAllAcceptableTickets(User user);
}
