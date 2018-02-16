package com.enterprise.yetanother.services.implementations;

import com.enterprise.yetanother.utilities.Commons;
import com.enterprise.yetanother.dao.interfaces.AttachmentDao;
import com.enterprise.yetanother.dao.interfaces.HistoryDao;
import com.enterprise.yetanother.dao.interfaces.TicketDao;
import com.enterprise.yetanother.dao.interfaces.UserDao;
import com.enterprise.yetanother.entities.Attachment;
import com.enterprise.yetanother.entities.History;
import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.entities.User;
import com.enterprise.yetanother.services.interfaces.AttachmentsService;
import com.enterprise.yetanother.services.interfaces.HistoryService;
import com.enterprise.yetanother.services.interfaces.UserService;
import com.enterprise.yetanother.utilities.Commons;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *@author andrey
 */
@Service
public class AttachmentsServiceImpl implements AttachmentsService {

    final static Logger LOGGER = LoggerFactory
                                 .getLogger(AttachmentsServiceImpl.class);

    @Autowired
    private AttachmentDao attachmentDao;

    @Autowired
    private UserService userService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private HistoryDao historyDao;

    @Autowired
    private TicketDao ticketDao;

    @Override
    public void create(Attachment attachment) {
        if (attachment != null) {
            attachmentDao.create(attachment);
        }
    }

    @Override
    public List<Attachment> getAttachments(Long ticket_id) {

        List<Attachment> attachments = null;
        if (ticket_id != null) {
            attachments = attachmentDao.findByTicket(ticket_id);
        }
        return attachments;
    }

    @Override
    public void delete(Long ticketId, Long attachmentId) {

        Ticket ticket = ticketDao.findTicketById(ticketId);
        User user = userDao.findByEmail(userService.getUserLogin());

        try {
            if (user.getId().compareTo(ticket.getOwner().getId()) != 0) {
                LOGGER.error("[delete: You are not a Ticket's owner!]");
                return;
            }
        } catch (NullPointerException e) {
            LOGGER.error("[delete: NullPointerException thrown!]", e);
            return;
        }

        try {
            String name = attachmentDao.findById(attachmentId).getFileName();
            attachmentDao.delete(attachmentId);
            List<String> fileNames = new ArrayList<>();
            fileNames.add(name);

            History history = historyService
                              .addHistoryOnFileDeletion(user, ticket, fileNames);
            historyDao.create(history);
            ticketDao.update(ticket);
        } catch (Exception e) {
            LOGGER.error("[delete: Exception thrown!]", e);
        }
    }

    @Override
    public void download(HttpServletResponse response,
                         Long ticketId, Long attachmentId) {
        Attachment attachment;

        if (attachmentId != null) {
            attachment = attachmentDao.findById(attachmentId);
        } else {
            LOGGER.warn("[download: attachmentId is Null!]");
            return;
        }

        int responseSize = 0;
        byte[] file = attachment.getBlob();
        responseSize += attachment.getBlob().length;
        String fileName = attachment.getFileName();

        if (file.length == 0) {
            LOGGER.warn("[download: Searched attachment is empty!]");
            return;
        }

        response.setContentType(Commons.defineContentType(fileName));
        response.setContentLength(responseSize);
        response.addHeader("Content-Disposition", "attachment; " +
                            "filename=" + fileName);

        try {
            IOUtils.copy(new ByteArrayInputStream(file),
                         response.getOutputStream());
        } catch (IOException e) {
            LOGGER.error("[download: IOException]", e);
        }
    }

    @Override
    public Attachment get(Long attachmentId) {
        if (attachmentId != null) {
            Attachment attachment = attachmentDao.findById(attachmentId);
            return attachment;
        } else {
            LOGGER.warn("[get: attachmentId is null!]");
            return null;
        }
    }
}