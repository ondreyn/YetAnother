package com.enterprise.yetanother.services.interfaces;

import com.enterprise.yetanother.entities.Attachment;
import com.enterprise.yetanother.entities.History;
import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.entities.User;
import com.enterprise.yetanother.enums.State;

import java.util.List;

/**
 *@author andrey
 */
public interface HistoryService {

    void create(History history);
    List<History> getCompleteHistory(Long ticket_id);
    List<History> getLatestHistory(Long ticket_id);
    History addHistoryOnFileAddition(User user, Ticket ticket,
                                     List<Attachment> attachments);
    History addHistoryOnFileDeletion(User user, Ticket ticket,
                                     List<String> fileNames);
    History addHistoryOnStateChange(User user, Ticket ticket, State prevState,
                                                              State newState);
    History addHistoryOnTicketEdit(User user, Ticket ticket);
    History addHistoryOnTicketCreation(User user, Ticket ticket);
    History addHistoryOnTicketApproveAssign(Ticket ticket, State newState,
                                                           User user);
}
