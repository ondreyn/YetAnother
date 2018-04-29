package com.enterprise.yetanother.controllers;

import com.enterprise.yetanother.dto.comment.CommentDto;
import com.enterprise.yetanother.dto.editticket.EditTicketDto;
import com.enterprise.yetanother.dto.feedback.FeedbackDto;
import com.enterprise.yetanother.dto.ticket.TicketDto;
import com.enterprise.yetanother.dto.user.UserDto;
import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.enums.State;
import com.enterprise.yetanother.enums.Urgency;
import com.enterprise.yetanother.services.interfaces.CategoryService;
import com.enterprise.yetanother.services.interfaces.TicketService;
/*import com.enterprise.yetanother.dto.feedback.FeedbackDto;
import com.enterprise.yetanother.dto.ticket.TicketDto;
import com.enterprise.yetanother.dto.user.UserDto;
import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.enums.State;
import com.enterprise.yetanother.enums.Urgency;
import com.enterprise.yetanother.dto.comment.CommentDto;
import com.enterprise.yetanother.dto.editticket.EditTicketDto;
import com.enterprise.yetanother.services.interfaces.CategoryService;
import com.enterprise.yetanother.services.interfaces.TicketService;*/
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 *@author andrey
 */
@Controller
@RequestMapping(value = "/")
public class InitController {

    final static Logger LOGGER = LoggerFactory.getLogger(InitController.class);

    private List<String> categories;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TicketService ticketService;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public ModelAndView toLoginPage() {
        LOGGER.info("[toLoginPage: GO /]");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Login");
        return modelAndView;
    }

    @RequestMapping(value = {"/favicon.ico"}, method = RequestMethod.GET)
    public String getFavicon() {
        LOGGER.info("[getFavicon: GO /favicon.ico]");
        return "forward:/resources/img/favicon.ico";
    }

    @RequestMapping(value = {"/postLogin"}, method = RequestMethod.POST)
    public String postLogin() {
        return "redirect:ticketsPage";
    }

//    @RequestMapping(value = {"/ticketsPage"}, method = {RequestMethod.GET})
//    public String toAllTicketsPage() {
//        LOGGER.info("[toAllTicketsPage: GO /ticketsPage]");
//        //ModelAndView modelAndView = new ModelAndView();
//        //modelAndView.setViewName("AllTickets");
//        return "AllTickets";
//    }

    @RequestMapping(value = {"/ticketsPage"}, method = {RequestMethod.GET})
    public ModelAndView toAllTicketsPage() {
        LOGGER.info("[toAllTicketsPage: GO /ticketsPage]");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("AllTickets");
        return modelAndView;
    }

    @RequestMapping(value = "/ticketCreatePage", method = RequestMethod.GET)
    public ModelAndView toTicketCreatePage() {
        LOGGER.info("[toTicketCreatePage: GO /ticketCreatePage]");

        if (categories == null) {
            categories = categoryService.getAllCategoriesNames();
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("CreateTicket");
        modelAndView.addObject("UserModel", new UserDto());
        modelAndView.addObject("TicketModel", new TicketDto());
        modelAndView.addObject("CommentModel", new CommentDto());
        modelAndView.addObject("allCategories", categories);
        modelAndView.addObject("allUrgencies", Urgency.values());
        return modelAndView;
    }

    @RequestMapping(value = "/ticketOverviewPage/{id}",
                    method = RequestMethod.GET)
    public ModelAndView toTicketOverview(@PathVariable("id") Long ticket_id) {
        LOGGER.info("[toTicketOverview: GO /ticketOverviewPage]");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("TicketOverview");
        modelAndView.addObject("TicketId", ticket_id);
        modelAndView.addObject("UserModel", new UserDto());
        modelAndView.addObject("CommentModel", new CommentDto());
        return modelAndView;
    }

    @RequestMapping(value = "/ticketEditPage/{id}", method = RequestMethod.GET)
    public ModelAndView toTicketEditPage(@PathVariable("id") Long id) {
        LOGGER.info("[toTicketEditPage: GO /editTicket]");
        Ticket ticket = ticketService.findTicketById(id);

        if (ticket == null) {
            LOGGER.warn("[toTicketEditPage: There is no such ticket!]");
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("AllTickets");
            return modelAndView;
        }

        if (ticket.getState().equals(State.DRAFT)) {
            if (categories == null) {
                categories = categoryService.getAllCategoriesNames();
            }

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("EditTicket");
            modelAndView.addObject("TicketId", id);
            modelAndView.addObject("OwnerId", ticket.getOwner().getId());
            modelAndView.addObject("UserModel", new UserDto());
            modelAndView.addObject("TicketModel", new TicketDto());
            modelAndView.addObject("CommentModel", new CommentDto());
            modelAndView.addObject("allCategories", categories);
            modelAndView.addObject("allUrgencies", Urgency.values());
            modelAndView.addObject("EditTicketModel", new EditTicketDto());
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("AllTickets");
            return modelAndView;
            //return toAllTicketsPage();
        }
    }

    @RequestMapping(value = "/ticketFeedbackPage/{id}/{action}",
                    method = RequestMethod.GET)
    public ModelAndView toFeedbackPage(@PathVariable("id") Long ticket_id,
                                       @PathVariable("action") String action) {

        LOGGER.info("[toFeedbackPage: GO /feedback]");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("FeedbackPage");
        modelAndView.addObject("TicketId", ticket_id);
        modelAndView.addObject("Action", action);
        modelAndView.addObject("FeedbackModel", new FeedbackDto());
        return modelAndView;
    }
}
