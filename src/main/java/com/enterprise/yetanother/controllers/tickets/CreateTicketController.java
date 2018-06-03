package com.enterprise.yetanother.controllers.tickets;

import com.enterprise.yetanother.dto.comment.CommentDto;
import com.enterprise.yetanother.dto.ticket.TicketDto;
import com.enterprise.yetanother.dto.user.UserDto;
import com.enterprise.yetanother.entities.Attachment;
import com.enterprise.yetanother.entities.Comment;
import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.entities.User;
import com.enterprise.yetanother.services.interfaces.TicketService;
import com.enterprise.yetanother.utilities.Commons;
import com.enterprise.yetanother.convertion.dtoconverters.interfaces.AttachmentDtoConverter;
import com.enterprise.yetanother.convertion.dtoconverters.interfaces.CommentDtoConverter;
import com.enterprise.yetanother.convertion.dtoconverters.interfaces.TicketDtoConverter;
import com.enterprise.yetanother.convertion.dtoconverters.interfaces.UserDtoConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

/**
 *@author andrey
 */
@RestController
public class CreateTicketController {

    final static Logger LOGGER = LoggerFactory
                                 .getLogger(CreateTicketController.class);

    private TicketDto ticketDto;
    private UserDto userDto;
    private boolean filesWorkedOut = false;
    private CommentDto commentDto;

    private User user;
    private Ticket ticket;
    private List<Attachment> attachments;
    private Comment comment;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserDtoConverter userDtoConverter;

    @Autowired
    private TicketDtoConverter ticketDtoConverter;

    @Autowired
    private AttachmentDtoConverter attachmentDtoConverter;

    @Autowired
    private CommentDtoConverter commentDtoConverter;

    @RequestMapping(value = "/tickets", method = RequestMethod.POST)
    public void getTicketModels(
            @ModelAttribute("UserModel") UserDto userDto,
            @ModelAttribute("TicketModel") TicketDto ticketDto,
            @ModelAttribute("CommentModel") CommentDto commentDto
    ) {
        LOGGER.info(String.format("[getTicketModels: \nUserDto has: %s\n" +
                    "TicketDto has: %s\nCommentDto has: %s]", userDto,
                    ticketDto, commentDto));

        try {
            this.userDto = userDto;
            this.user = userDtoConverter.dtoToEntity(userDto);
            this.ticketDto = ticketDto;
            this.ticket = ticketDtoConverter.dtoToEntity(ticketDto);
            this.commentDto = commentDto;
            this.comment = commentDtoConverter.dtoToEntity(commentDto);

            if (this.user != null) {
                LOGGER.info("[getTicketModels: UserDto worked]");
            } else {
                LOGGER.warn("[getTicketModels: UserDto has errors!]");
                return;
            }
            if (this.ticket != null) {
                LOGGER.info("[getTicketModels: TicketDto worked]");
            } else {
                LOGGER.warn("[getTicketModels: TicketDto has errors!]");
                return;
            }
            if (this.comment != null) {
                LOGGER.info("[getTicketModels: commentDto worked]");
            }
        } catch (Exception e) {
            LOGGER.error("[getTicketModels: Exception thrown!]", e);
            return;
        }

        if (this.filesWorkedOut) {
            LOGGER.info("[getTicketModels: It's OK, GO ON]");
            createTicket();
        }
    }

    @RequestMapping(value = "/tickets/attachments", method = RequestMethod.POST)
    public void getAttachments(MultipartHttpServletRequest request) {

        try {
            this.filesWorkedOut = true;
            MultipartFile[] files = Commons.getAttachments(request);
            this.attachments = attachmentDtoConverter.transform(files);

            if (attachments != null) {
                LOGGER.warn("[getAttachments: attachments worked]");
            }
        } catch (Exception e) {
            LOGGER.error("[getAttachments: Exception thrown!]", e);
            return;
        }

        if (this.ticket != null && this.user != null
                                && this.commentDto != null) {
            LOGGER.info("[getAttachments: It's OK, GO ON]");
            createTicket();
        }
    }

    private void createTicket() {
        LOGGER.info(String.format("[createTicket: we have: \nticketDto: %s" +
                    "\nfilesWorkedOut: %b\ncommentDto: %s\nuserDto: %s\n" +
                    "user: %s\nticket: %s]", this.ticketDto, this.filesWorkedOut,
                    this.commentDto, this.userDto, this.user, this.ticket));

        if (this.ticketDto != null
                && this.filesWorkedOut
                && this.commentDto != null
                && this.userDto != null
                && this.user != null
                && this.ticket != null) {

            LOGGER.info("[createTicket: It's OK, GO ON]");
            ticketService.createTicket(this.ticket, this.user,
                                       this.attachments, this.comment);

            this.ticket = null;
            this.attachments = null;
            this.comment = null;
            this.user = null;

            this.filesWorkedOut = false;
            this.commentDto = null;
            this.ticketDto = null;
            this.userDto = null;
        }
    }
}