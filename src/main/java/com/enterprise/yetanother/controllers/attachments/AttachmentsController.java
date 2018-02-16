package com.enterprise.yetanother.controllers.attachments;

import com.enterprise.yetanother.dto.attachments.AttachmentDto;
import com.enterprise.yetanother.entities.Attachment;
import com.enterprise.yetanother.services.interfaces.AttachmentsService;
import com.enterprise.yetanother.utilities.Commons;
import com.enterprise.yetanother.convertion.dtoconverters.interfaces.AttachmentDtoConverter;
import com.enterprise.yetanother.entities.Attachment;
import com.enterprise.yetanother.dto.attachments.AttachmentDto;
import com.enterprise.yetanother.services.interfaces.AttachmentsService;
import com.enterprise.yetanother.utilities.Commons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *@author andrey
 */
@RestController
@RequestMapping(value = "/tickets/{id}")
public class AttachmentsController {

    final static Logger LOGGER = LoggerFactory
                                 .getLogger(AttachmentsController.class);
    @Autowired
    private AttachmentsService attachmentsService;

    @Autowired
    private AttachmentDtoConverter attachmentDtoConverter;

    @RequestMapping(value = "/attachments",
                    method = RequestMethod.GET,
                    produces = "application/json")
    public List<AttachmentDto> getAttachments(@PathVariable("id") Long id) {
        List<Attachment> attachments = attachmentsService.getAttachments(id);

        if (Commons.isNulls(attachments)) {
            LOGGER.warn("[getAttachments: nothing found!]");
            return null;
        }

        if (!attachments.isEmpty()) {
            return attachmentDtoConverter.entitiesToDtos(attachments);
        } else {
            LOGGER.warn("[getAttachments: nothing found!]");
            return null;
        }

//        if (attachments != null || !attachments.isEmpty()) {
//            return attachmentDtoConverter.entitiesToDtos(attachments);
//        } else {
//            LOGGER.warn("[getAttachments: nothing found!]");
//            return null;
//        }
    }

    @RequestMapping(value = "/attachments/{attId}",
                    method = RequestMethod.DELETE)
    public void deleteAttachment(@PathVariable("id") Long ticketId,
                                 @PathVariable("attId") Long attachmentId) {

        if (ticketId != null && attachmentId != null) {
            LOGGER.info("[deleteAttachment:\ngot ticket ID: " + ticketId
                        + "\ngot attachment IDs: " + attachmentId + "]");

            attachmentsService.delete(ticketId, attachmentId);
        }
    }

    @RequestMapping(value = "/attachments/{attId}", method = RequestMethod.GET)
    public void downloadAttachment(@PathVariable("id") Long ticketId,
                                   @PathVariable("attId") Long attachmentId,
                                   HttpServletResponse response) {

        if (ticketId != null && attachmentId != null) {
            LOGGER.info("[downloadAttachment: attachmentsId " + attachmentId
                        + ", ticketId " + ticketId + "]");
            attachmentsService.download(response, ticketId, attachmentId);
        }
    }
}
