package com.enterprise.yetanother.controllers.comments;

import com.enterprise.yetanother.dto.comment.CommentDto;
import com.enterprise.yetanother.entities.Comment;
import com.enterprise.yetanother.init.Properties;
import com.enterprise.yetanother.services.interfaces.CommentsService;
import com.enterprise.yetanother.services.interfaces.TicketService;
import com.enterprise.yetanother.services.interfaces.UserService;
import com.enterprise.yetanother.convertion.dtoconverters.interfaces.CommentDtoConverter;
import com.enterprise.yetanother.dto.comment.CommentDto;
import com.enterprise.yetanother.entities.Comment;
import com.enterprise.yetanother.init.Properties;
import com.enterprise.yetanother.services.interfaces.CommentsService;
import com.enterprise.yetanother.services.interfaces.TicketService;
import com.enterprise.yetanother.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *@author andrey
 */
@RestController
@RequestMapping(value = "/tickets/{id}")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentDtoConverter commentDtoConverter;

    @RequestMapping(value = "/comments/" + Properties.COMMENTS_LENGTH,
                    method = RequestMethod.GET,
                    produces = "application/json")
    public List<CommentDto> getLatestComments(@PathVariable("id") Long id) {
        return commentDtoConverter.entitiesToDtos(
                commentsService.getLatestComments(id)
        );
    }

    @RequestMapping(value = "/comments",
                    method = RequestMethod.GET,
                    produces = "application/json")
    public List<CommentDto> getCompleteComments(@PathVariable("id") Long id) {
        return commentDtoConverter.entitiesToDtos(
                commentsService.getCompleteComments(id)
        );
    }

    @RequestMapping(value = "/comments", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void addComment(
            @PathVariable("id") Long ticketId,
            @ModelAttribute("CommentModel") CommentDto commentDto
    ) {
        Comment comment = commentDtoConverter.dtoToEntity(commentDto);
        comment.setTicket(ticketService.findTicketById(ticketId));
        comment.setUser(userService.getUserByEmail(userService.getUserLogin()));

        commentsService.addComment(comment);
    }
}
