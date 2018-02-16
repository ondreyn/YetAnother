package com.enterprise.yetanother.controllers.feedback;

import com.enterprise.yetanother.dto.feedback.FeedbackDto;
import com.enterprise.yetanother.entities.Feedback;
import com.enterprise.yetanother.services.interfaces.FeedbackService;
import com.enterprise.yetanother.services.interfaces.TicketService;
import com.enterprise.yetanother.services.interfaces.UserService;
import com.enterprise.yetanother.convertion.dtoconverters.interfaces.FeedbackDtoConverter;
import com.enterprise.yetanother.dto.feedback.FeedbackDto;
import com.enterprise.yetanother.entities.Feedback;
import com.enterprise.yetanother.services.interfaces.FeedbackService;
import com.enterprise.yetanother.services.interfaces.TicketService;
import com.enterprise.yetanother.services.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *@author andrey
 */
@RestController
@RequestMapping(value = "/tickets")
public class FeedbackController {

    final static Logger LOGGER = LoggerFactory
                                 .getLogger(FeedbackController.class);

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    @Autowired
    private FeedbackDtoConverter feedbackDtoConverter;

    @RequestMapping(value = "/{id}/feedbacks", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void leaveFeedback(
            @PathVariable("id") Long ticketId,
            @ModelAttribute("FeedbackModel") FeedbackDto feedbackDto
    ) {
        LOGGER.info("[leaveFeedback: GO]");
        if (feedbackDto != null) {
            Feedback feedback = feedbackDtoConverter.dtoToEntity(feedbackDto);
            feedback.setTicket(ticketService.findTicketById(ticketId));
            feedback.setUser(
                    userService.getUserByEmail(userService.getUserLogin())
            );

            feedbackService.create(feedback);
        } else {
            LOGGER.warn("[leaveFeedback: feedbackDto is null!]");
        }
    }

    @RequestMapping(value = "/{id}/feedbacks", method = RequestMethod.GET,
                    produces = "application/json")
    public FeedbackDto getFeedback(@PathVariable("id") Long id) {
        return feedbackDtoConverter.entityToDto(feedbackService.getFeedback(id));
    }

    @RequestMapping(value = "/feedbacks", method = RequestMethod.GET,
                    produces = "application/json")
    public List<FeedbackDto> getAllFeedback() {
        return feedbackDtoConverter.entitiesToDtos(feedbackService.getAllFeedback());
    }
}
