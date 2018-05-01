package com.enterprise.yetanother.services.implementations;

import com.enterprise.yetanother.utilities.Commons;
import com.enterprise.yetanother.dao.interfaces.*;
import com.enterprise.yetanother.entities.*;
import com.enterprise.yetanother.enums.Roles;
import com.enterprise.yetanother.enums.State;
import com.enterprise.yetanother.init.Properties;
import com.enterprise.yetanother.services.interfaces.EmailService;
import com.enterprise.yetanother.services.interfaces.HistoryService;
import com.enterprise.yetanother.services.interfaces.TicketService;
import com.enterprise.yetanother.services.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

/**
 *@author andrey
 */
@Service
public class TicketServiceImpl implements TicketService {

    final static Logger LOGGER = LoggerFactory
                                 .getLogger(TicketServiceImpl.class);

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private HistoryDao historyDao;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private AttachmentDao attachmentDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Override
    public void createTicket(Ticket ticket, User user,
                             List<Attachment> attachments,
                             Comment comment
    ) {
        LOGGER.info("[createTicket: GO]");

        if (ticket == null) {
            LOGGER.error("[createTicket: ticket is null!]");
            return;
        }

        ticket.setHistories(new ArrayList<>());
        ticket.setComments(new ArrayList<>());
        ticket.setAttachments(new ArrayList<>());

        if (user == null) {
            LOGGER.error("[createTicket: user is null!]");
            return;
        }

        ticket.setOwner(user);

        if (attachments != null) {
            for (Attachment attachment: attachments) {
                attachment.setTicket(ticket);
                ticket.getAttachments().add(attachment);
            }
        }

        if (comment != null && !comment.getText().isEmpty()) {            
            comment.setTicket(ticket);
            comment.setUser(user);
            ticket.getComments().add(comment);            
        }        

        List<History> histories = new ArrayList<>();
        History creationHistory = historyService
                                  .addHistoryOnTicketCreation(user, ticket);

        if (creationHistory != null) {
            histories.add(creationHistory);
        }

        History filesHistory = historyService.addHistoryOnFileAddition(
                                              user, ticket, attachments);
        if (filesHistory != null) {
            histories.add(filesHistory);
        }

        ticket.setHistories(histories);

        try {
            ticketDao.create(ticket);
            LOGGER.info("[createTicket: Ticket created id: " + ticket.getId()
                        + "]");
            if (emailService.isEnabled() &&	ticket.getState() == State.NEW) {
                try {
                    List<User> managers = userService.getAllManagers();
                    emailService.sendBroadcastMail(ticket, managers,
                                                   Properties.NEW_TICKET);
                } catch (MessagingException e) {
                    LOGGER.error("[createTicket: MessagingException " +
                                 "thrown!]", e);
                }
            }
        } catch (Exception e) {
            LOGGER.error("[createTicket: Exception thrown!]", e);
        }
    }

    @Override
    public Ticket findTicketById(Long ticketId) {
        if (ticketId != null) {
            return ticketDao.findTicketById(ticketId);
        } else {
            return null;
        }
    }

    @Override
    public void editTicket(Ticket ticket, User user,
                           List<Attachment> attachments,
                           Comment comment, Long ticketId, Long ownerId
    ) {
        LOGGER.info("[editTicket: GO]");

        if (ticket == null) {
            LOGGER.error("[editTicket: ticket is null!]");
            return;
        }

        if (ticketId == null) {
            LOGGER.error("[editTicket: did you want to create a ticket?]");
            return;
        }

        ticket.setId(ticketId);

        if (ownerId.longValue() != user.getId().longValue()) {
            LOGGER.error("[editTicket: not the ticket's owner!]");
            return;
        }

        ticket.setOwner(user);

        if (attachments != null) {
            for (Attachment attachment: attachments) {
                attachment.setTicket(ticket);
                attachmentDao.create(attachment);
            }
        }

        if (comment != null) {
            try {
                if (!comment.getText().isEmpty()) {
                    comment.setUser(user);
                    comment.setTicket(ticket);
                    commentDao.create(comment);
                }
            } catch (NullPointerException e) {
                LOGGER.warn("[editTicket: NullPointerException thrown]");
            }
        }

        /*if (attachments != null && !attachments.isEmpty()) {            
            History filesHistory = historyService.addHistoryOnFileAddition(
                                   ticket.getOwner(), ticket, attachments);
            if (filesHistory != null) {
                historyDao.create(filesHistory);
            }            
        }*/
        
        if (attachments != null) {
            if (!attachments.isEmpty()) {
                History filesHistory = historyService.addHistoryOnFileAddition(
                                       ticket.getOwner(), ticket, attachments);
                if (filesHistory != null) {
                    historyDao.create(filesHistory);
                }
            }
        }

        History editHistory = historyService
                             .addHistoryOnTicketEdit(ticket.getOwner(), ticket);
        if (editHistory != null) {
            historyDao.create(editHistory);
        }

        try {
            ticketDao.update(ticket);
            LOGGER.info("[editTicket: edited Ticket with ID: " + ticket.getId
                    () + "]");

            if (emailService.isEnabled() &&	ticket.getState().equals(State.NEW)) {
                try {
                    List<User> managers = userService.getAllManagers();
                    emailService.sendBroadcastMail(ticket, managers,
                                                   Properties.NEW_TICKET);
                } catch (MessagingException e) {
                    LOGGER.error("[editTicket: MessagingException]", e);
                }
            }
        } catch (Exception e) {
            LOGGER.error("[editTicket: Exception]", e);
        }
    }

    @Override
    public void updateTicket(Ticket ticket) {
        if (ticket != null) {
            ticketDao.update(ticket);
        } else {
            LOGGER.warn("[updateTicket: ticket is null!]");
        }
    }

    @Override
    public void editState(Long ticket_id, String action, State state,
                          String email) {
        LOGGER.info("[editState: GO]");

        boolean check = Commons.isNulls(ticket_id, action, state, email);
        if (check) {
            if (ticket_id == null) {
                LOGGER.warn("[editState: ticket_id is Null]");
            }
            if (action == null) {
                LOGGER.warn("[editState: action is Null]");
            }
            if (state == null) {
                LOGGER.warn("[editState: state is Null]");
            }
            if (email == null) {
                LOGGER.warn("[editState: email is Null]");
            }
            return;
        }

        LOGGER.info("[editState: got: Id=" + ticket_id + ", action="
                    + action + ", state=" + state.toString() + ", user=" + email +"]");
        User user = userDao.findByEmail(email);
        Ticket ticket = ticketDao.findTicketById(ticket_id);

        if (user == null) {
            LOGGER.warn("[editState: The User was not found!]");
            return;
        }

        if (ticket == null) {
            LOGGER.warn("[editState: The Ticket was not found!]");
            return;
        }

        if (user.getRole() != Roles.ENGINEER
                && (state == State.DRAFT || state == State.DECLINED)) {
            LOGGER.info("[editState: term 1 worked]");
            doWithEmployeeAndManager(ticket, user, action, state);
            return;
        }

        if (user.getRole() == Roles.MANAGER && state == State.NEW) {
            LOGGER.info("[editState: term 2 worked]");
            doWithManager(ticket, user, action, state);
            return;
        }

        if (user.getRole() == Roles.MANAGER
                && action.equalsIgnoreCase("Cancel")) {
            LOGGER.info("[editState: term 3 worked]");
            doWithManager(ticket, user, action, state);
            return;
        }

        if (user.getRole() == Roles.ENGINEER) {
            LOGGER.info("[editState: term 4 worked]");
            doWithEngineer(ticket, user, action, state);
            return;
        }

        LOGGER.warn("[editState: NO MATCH!]");
    }

    private void doWithEmployeeAndManager(Ticket ticket, User user,
                                          String action, State state) {
        LOGGER.info("[doWithEmployeeAndManager: GO]");

        History history;

        if (action.equalsIgnoreCase("Submit")) {
            ticketDao.setState(ticket, State.NEW);
            /*history = historyService.addHistoryOnStateChange(user,
                    ticket, State.NEW, state);*/
            history = historyService.addHistoryOnStateChange(user,
                    ticket, state, State.NEW);
            historyDao.create(history);
            if (emailService.isEnabled()) {
            	try {
            		List<User> managers = userService.getAllManagers();
            		emailService.sendBroadcastMail(ticket, managers,
                        Properties.NEW_TICKET);
            	} catch (MessagingException e) {
            		LOGGER.error("[doWithEmployeeAndManager: MessagingException!]", e);
            	}
            }
            return;
        }
        if (action.equalsIgnoreCase("Cancel")) {
            ticketDao.setState(ticket, State.CANCELED);
            history = historyService.addHistoryOnStateChange(user,
                    ticket, state, State.CANCELED);
            historyDao.create(history);
        }
    }

    private void doWithManager(Ticket ticket, User user,
                               String action, State state) {
        LOGGER.info("[doWithManager: GO]");

        History history;

        if (action.equalsIgnoreCase("Approve")) {
            //ticketDao.approve(ticket, State.APPROVED, user);
            history = historyService.addHistoryOnTicketApproveAssign(
                    ticket, State.APPROVED, user);
            ticketDao.approve(ticket, State.APPROVED, user);
            historyDao.create(history);
            if (emailService.isEnabled()) {
            	try {
            		List<User> engineers = userService.getAllEngineers();
            		User creator = userService.getCreator(ticket);
            		engineers.add(creator);
            		emailService.sendBroadcastMail(ticket, engineers,
                        Properties.APPROVED_BY_MANAGER);
            	} catch (MessagingException e) {
            		LOGGER.error("[doWithManager: MessagingException!]", e);
            	}
            }
            return;
        }

        if (action.equalsIgnoreCase("Decline")) {
            //State prevState = ticket.getState();
            //ticketDao.approve(ticket, State.DECLINED, user);
            history = historyService.addHistoryOnTicketApproveAssign(
                    ticket, State.DECLINED, user);
            ticketDao.approve(ticket, State.DECLINED, user);
            historyDao.create(history);
            if (emailService.isEnabled()) {
            	try {
            		User creator = userService.getCreator(ticket);
            		emailService.sendPersonalMail(ticket, creator,
                        Properties.DECLINED_BY_MANAGER);
            	} catch (MessagingException e) {
            		LOGGER.error("[doWithManager: MessagingException!]", e);
            	}
            }
            return;
        }

        if (state == State.DRAFT || state == State.DECLINED) {
            //ticketDao.approve(ticket, State.CANCELED, user);
            history = historyService.addHistoryOnTicketApproveAssign(
                    ticket, State.CANCELED, user);
            ticketDao.approve(ticket, State.CANCELED, user);
            historyDao.create(history);
            return;
        }
        if (state == State.NEW) {
            //ticketDao.approve(ticket, State.CANCELED, user);
            history = historyService.addHistoryOnTicketApproveAssign(
                    ticket, State.CANCELED, user);
            ticketDao.approve(ticket, State.CANCELED, user);
            historyDao.create(history);
            if (emailService.isEnabled()) {
            	try {
            		User creator = userService.getCreator(ticket);
            		emailService.sendPersonalMail(ticket, creator,
                        Properties.CANCELLED_BY_MANAGER);
            	} catch (MessagingException e) {
            		LOGGER.error("[doWithManager: MessagingException!]", e);
            	}
            }
        }
    }

    private void doWithEngineer(Ticket ticket, User user,
                               String action, State state) {
        LOGGER.info("[doWithEngineer: GO]");

        History history;

        if (action.equalsIgnoreCase("Assign to Me")) {
            if (state == State.APPROVED) {
                //ticketDao.assign(ticket, State.IN_PROGRESS, user);
                history = historyService.addHistoryOnTicketApproveAssign
                        (ticket, State.IN_PROGRESS, user);
                ticketDao.assign(ticket, State.IN_PROGRESS, user);
                historyDao.create(history);
            }
            return;
        }
        if (action.equalsIgnoreCase("Cancel")) {
            if (state == State.APPROVED) {
                //ticketDao.assign(ticket, State.CANCELED, user);
                history = historyService.addHistoryOnTicketApproveAssign
                        (ticket, State.CANCELED, user);
                ticketDao.assign(ticket, State.CANCELED, user);
                historyDao.create(history);
                if (emailService.isEnabled()) {
                	try {
                		List<User> recipients = new ArrayList<>();
                		recipients.add(userService.getApprover(ticket));
                		recipients.add(userService.getCreator(ticket));
                		emailService.sendBroadcastMail(ticket, recipients,
                            Properties.CANCELLED_BY_ENGINEER);
                	} catch (MessagingException e) {
                		LOGGER.error("[doWithEngineer: MessagingException!]", e);
                	}
                }
            }
            return;
        }
        if (action.equalsIgnoreCase("Done") && state == State.IN_PROGRESS) {            
            //ticketDao.setState(ticket, State.DONE);
            history = historyService.addHistoryOnStateChange(
                      user, ticket, state, State.DONE);
            ticketDao.setState(ticket, State.DONE);
            historyDao.create(history);

            if (emailService.isEnabled()) {
               	try {
               		User creator = userService.getCreator(ticket);
               		emailService.sendPersonalMail(ticket, creator,
               									  Properties.DONE_BY_ENGINEER);
               	} catch (MessagingException e) {
               		LOGGER.error("[doWithEngineer: MessagingException!]", e);
               	}
            }           
        }
        
        /*if (action.equalsIgnoreCase("Done")) {
            if (state == State.IN_PROGRESS) {
                //ticketDao.setState(ticket, State.DONE);
                history = historyService.addHistoryOnStateChange(
                          user, ticket, state, State.DONE);
                ticketDao.setState(ticket, State.DONE);
                historyDao.create(history);
                
                if (emailService.isEnabled()) {
                    try {
                        User creator = userService.getCreator(ticket);
                        emailService.sendPersonalMail(ticket, creator,
                                                      Properties.DONE_BY_ENGINEER);
                    } catch (MessagingException e) {
                        LOGGER.error("[doWithEngineer: MessagingException!]", e);
                    }
                }
            }
        }*/
    }
}