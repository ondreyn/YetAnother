package com.enterprise.yetanother.services.implementations;

import com.enterprise.yetanother.utilities.Commons;
import com.enterprise.yetanother.dao.interfaces.HistoryDao;
import com.enterprise.yetanother.entities.Attachment;
import com.enterprise.yetanother.entities.History;
import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.entities.User;
import com.enterprise.yetanother.enums.State;
import com.enterprise.yetanother.init.Properties;
import com.enterprise.yetanother.services.interfaces.HistoryService;
//import com.enterprise.yetanother.utilities.Commons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 *@author andrey
 */
@Service
public class HistoryServiceImpl implements HistoryService {

    final static Logger LOGGER = LoggerFactory
                                 .getLogger(HistoryServiceImpl.class);

    @Autowired
    private HistoryDao historyDao;

    @Override
    public void create(History history) {
        if (history != null) {
            historyDao.create(history);
        }
    }

    @Override
    public List<History> getLatestHistory(Long ticket_id) {
        if (ticket_id != null) {
            return historyDao.findByTicket(ticket_id, Properties.HISTORY_LENGTH);
        } else {
            return null;
        }
    }

    @Override
    public List<History> getCompleteHistory(Long ticket_id) {
        if (ticket_id != null) {
            return historyDao.findByTicket(ticket_id, 0);
        } else {
            return null;
        }
    }

    @Override
    public History addHistoryOnFileAddition(User user, Ticket ticket,
                                            List<Attachment> attachments) {
        boolean check = Commons.isNulls(user, ticket, attachments);
        if (check) {
            LOGGER.warn("[addHistoryOnFileAddition: null/s in incoming args!]");
            return null;
        }

        List<String> fileNames = Commons.getAttachmentsFileNames(attachments);

        if (fileNames != null) {
            if (!fileNames.isEmpty()) {
                LOGGER.info("[addHistoryOnFileAddition: " + fileNames.size()
                            + " files]");

                String description = Commons.getDescriptionOnFilesAttach
                                     (fileNames, "File is attached");

                LOGGER.info("[addHistoryOnFileAddition: " + description + "]");

                return new History.HistoryBuilder()
                        .action("File is attached")
                        .date(new Date())
                        .description(description)
                        .ticket(ticket)
                        .user(user)
                        .build();
            } else {
                LOGGER.warn("[addHistoryOnFileAddition: fileNames are empty!]");
            }
        } else {
            LOGGER.warn("[addHistoryOnFileAddition: fileNames is null!]");
        }
        return null;
    }

    @Override
    public History addHistoryOnFileDeletion(User user, Ticket ticket,
                                            List<String> fileNames) {

        boolean check = Commons.isNulls(user, ticket, fileNames);
        if (check) {
            LOGGER.warn("[addHistoryOnFileDeletion: null/s in incoming args!]");
            return null;
        }

        if (!fileNames.isEmpty()) {

            String description = Commons.getDescriptionOnFilesAttach
                                 (fileNames, "File is removed");
            LOGGER.info("addHistoryOnFileDeletion: " + description);

            return new History.HistoryBuilder()
                    .action("File is removed")
                    .date(new Date())
                    .description(description)
                    .ticket(ticket)
                    .user(user)
                    .build();
        } else {
            LOGGER.warn("[addHistoryOnFileDeletion: fileNames are empty!]");
        }
        return null;
    }

    @Override
    public History addHistoryOnStateChange(User user, Ticket ticket,
                                           State prevState, State newState) {

        boolean check = Commons.isNulls(user, ticket, prevState, newState);
        if (check) {
            LOGGER.warn("[addHistoryOnStateChange: null/s in incoming args!]");
            return null;
        }

        String description = "Ticket Status is changed from "
                             + prevState.toString() + " to " + newState
                             .toString();

        return new History.HistoryBuilder()
                .action("Ticket Status is changed")
                .date(new Date())
                .description(description)
                .ticket(ticket)
                .user(user)
                .build();
    }

    @Override
    public History addHistoryOnTicketEdit(User user, Ticket ticket) {

        boolean check = Commons.isNulls(user, ticket);
        if (check) {
            LOGGER.warn("[addHistoryOnTicketEdit: null/s in incoming args!]");
            return null;
        }

        return new History.HistoryBuilder()
                .action("Ticket is edited")
                .date(new Date())
                .description("Ticket is edited")
                .ticket(ticket)
                .user(user)
                .build();
    }

    @Override
    public History addHistoryOnTicketCreation(User user, Ticket ticket) {

        boolean check = Commons.isNulls(user, ticket);
        if (check) {
            LOGGER.warn("[addHistoryOnTicketCreation: null/s in incoming args!]");
            return null;
        }

        return new History.HistoryBuilder()
                .action("Ticket is created")
                .date(new Date())
                .description("Ticket is created")
                .ticket(ticket)
                .user(user)
                .build();
    }

    @Override
    public History addHistoryOnTicketApproveAssign(Ticket ticket,
                                                   State newState, User user) {

        boolean check = Commons.isNulls(user, ticket, newState);
        if (check) {
            LOGGER.warn("[addHistoryOnTicketApproveAssign: null/s in " +
                        "incoming args!]");
            return null;
        }

        String description = "Ticket Status is changed from " +
                             ticket.getState().toString() + " to " + newState
                             .toString();

        return new History.HistoryBuilder()
                .action("Ticket Status is changed")
                .date(new Date())
                .description(description)
                .ticket(ticket)
                .user(user)
                .build();
    }
}