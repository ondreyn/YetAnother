package com.enterprise.yetanother.services.interfaces;

import com.enterprise.yetanother.entities.Attachment;
import com.enterprise.yetanother.entities.Comment;
import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.entities.User;
import com.enterprise.yetanother.enums.State;

import java.util.List;

/**
 *@author andrey
 */
public interface TicketService {

    void createTicket(Ticket ticket, User user, List<Attachment> attachments,
                      Comment comment);
    Ticket findTicketById(Long ticketId);
    void editState(Long ticket_id, String action, State state, String userName);
    void editTicket(Ticket ticket, User user, List<Attachment> attachments,
                    Comment comment, Long ticketId, Long ownerId);
    void updateTicket(Ticket ticket);
}
