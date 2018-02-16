package com.enterprise.yetanother.services.interfaces;

import com.enterprise.yetanother.entities.Attachment;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *@author andrey
 */
public interface AttachmentsService {

    void create(Attachment attachment);
    List<Attachment> getAttachments(Long ticket_id);
    void delete(Long ticketId, Long attachmentId);
    void download(HttpServletResponse response, Long ticketId,
                                                Long attachmentId);
    Attachment get(Long attachmentId);
}
