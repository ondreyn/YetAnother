package com.enterprise.yetanother.controllers.tickets;

import com.enterprise.yetanother.dto.comment.CommentDto;
import com.enterprise.yetanother.dto.ticket.TicketDto;
import com.enterprise.yetanother.dto.user.UserDto;
import com.enterprise.yetanother.entities.Attachment;
import com.enterprise.yetanother.entities.Comment;
import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.entities.User;
import com.enterprise.yetanother.enums.State;
import com.enterprise.yetanother.services.interfaces.TicketService;
import com.enterprise.yetanother.utilities.Commons;
import com.enterprise.yetanother.convertion.dtoconverters.interfaces.AttachmentDtoConverter;
import com.enterprise.yetanother.convertion.dtoconverters.interfaces.CommentDtoConverter;
import com.enterprise.yetanother.convertion.dtoconverters.interfaces.TicketDtoConverter;
import com.enterprise.yetanother.convertion.dtoconverters.interfaces.UserDtoConverter;
import com.enterprise.yetanother.dto.ticket.TicketDto;
import com.enterprise.yetanother.dto.user.UserDto;
import com.enterprise.yetanother.entities.Attachment;
import com.enterprise.yetanother.entities.Comment;
import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.entities.User;
import com.enterprise.yetanother.enums.State;
import com.enterprise.yetanother.dto.comment.CommentDto;
import com.enterprise.yetanother.services.interfaces.TicketService;
import com.enterprise.yetanother.utilities.Commons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.LinkedHashMap;
import java.util.List;

/**
 *@author andrey
 */
@RestController
@RequestMapping(value = "/tickets")
public class EditTicketController {

    final static Logger LOGGER = LoggerFactory
                                 .getLogger(EditTicketController.class);

    private TicketDto ticketDto;

    private CommentDto commentDto;

    private Long ticketId;
    private Long ownerId;
    private UserDto userDto;
    private boolean filesWorkedOut = false;

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

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void editTicketModels(
            @ModelAttribute("UserModel") UserDto userDto,
            @ModelAttribute("TicketModel") TicketDto ticketDto,
            @ModelAttribute("CommentModel") CommentDto commentDto,
            @ModelAttribute("OwnerId") Long ownerId,
            @PathVariable("id") Long id
    ) {
        LOGGER.info("[editTicketModels: \nUserDto has: " + userDto
                    .toString() + "\nTicketDto has: " + ticketDto.toString()
                    + "\nCommentDto has: " + commentDto.toString() + "]");
        try {
            this.ownerId = ownerId;
            this.ticketId = id;
            this.userDto = userDto;
            this.user = userDtoConverter.dtoToEntity(userDto);
            this.ticketDto = ticketDto;
            this.ticket = ticketDtoConverter.dtoToEntity(ticketDto);
            this.commentDto = commentDto;
            this.comment = commentDtoConverter.dtoToEntity(commentDto);

            if (this.user != null) {
                LOGGER.info("[editTicketModels: UserDto worked]");
            } else {
                LOGGER.warn("[editTicketModels: UserDto has errors!]");
                return;
            }
            if (this.ticket != null) {
                LOGGER.info("[editTicketModels: TicketDto worked]");
            } else {
                LOGGER.warn("[editTicketModels: TicketDto has errors!]");
                return;
            }
            if (this.comment != null) {
                LOGGER.info("[editTicketModels: commentDto worked]");
            }
        } catch (Exception e) {
            LOGGER.error("[editTicketModels: Exception thrown!]", e);
            return;
        }

        if (this.filesWorkedOut) {
            LOGGER.info("[editTicketModels: It's OK, GO ON]");
            editTicket();
        }
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.PATCH},
                    consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editTicketState(
            @PathVariable("id") Long id,
            @RequestBody LinkedHashMap<String, String>[] models
    ) {
        LOGGER.info("[editTicketState: GO]");

        try {
            String action = models[0].get("action");
            State state = State.valueOf(models[0].get("state"));
            String email = models[1].get("email");

            ticketService.editState(id, action, state, email);
        } catch (Exception e) {
            LOGGER.error("[editTicketState: Exception]", e);
        }
    }


    @RequestMapping(value = "/{id}/attachments", method = {RequestMethod.POST})
    public void getAttachments(MultipartHttpServletRequest request,
                               @PathVariable("id") Long id) {

        try {
            this.filesWorkedOut = true;
            MultipartFile[] files = Commons.getAttachments(request);
            this.attachments = attachmentDtoConverter.transform(files);
            if (this.attachments != null) {
                LOGGER.info("[getAttachments: attachments worked]");
            }
        } catch (Exception e) {
            LOGGER.error("[getAttachments: Exception thrown!]", e);
            return;
        }

        if (this.commentDto != null && this.ticket != null
                && this.user != null && this.ownerId != null
                && this.ticketId != null) {

            LOGGER.info("[getAttachments: It's OK, GO ON]");
            editTicket();
        }
    }

    private void editTicket() {
        LOGGER.info("[editTicket: we have: \nticketDto: " + this.ticketDto +
                    "\nfilesWorkedOut: " + this.filesWorkedOut +
                    "\ncommentDto: " + this.commentDto + "\nuserDto: " +
                    this.userDto + "\nuser: " + this.user + "\nthis.ticket: "
                    + this.ticket + "]");

        if (this.ticketDto != null && this.filesWorkedOut
                && this.commentDto != null && this.userDto != null
                && this.user != null && this.ticket != null) {

            LOGGER.info("[editTicket: It's OK, GO ON]");
            ticketService.editTicket(this.ticket, this.user, this.attachments,
                                     this.comment, this.ticketId, this.ownerId);

            this.ticket = null;
            this.attachments = null;
            this.comment = null;
            this.user = null;
            this.filesWorkedOut = false;
            this.commentDto = null;
        }
    }
}
