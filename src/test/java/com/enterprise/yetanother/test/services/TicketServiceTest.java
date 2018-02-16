package com.enterprise.yetanother.test.services;

import com.enterprise.yetanother.dao.interfaces.CategoryDao;
import com.enterprise.yetanother.dao.interfaces.TicketDao;
import com.enterprise.yetanother.dao.interfaces.UserDao;
import com.enterprise.yetanother.entities.*;
import com.enterprise.yetanother.enums.State;
import com.enterprise.yetanother.enums.Urgency;
import com.enterprise.yetanother.services.interfaces.TicketService;
import com.enterprise.yetanother.test.configuration.PersistenceConfigTest;
import com.enterprise.yetanother.test.configuration.WebAppConfigTest;
import com.enterprise.yetanother.dao.interfaces.CategoryDao;
import com.enterprise.yetanother.dao.interfaces.TicketDao;
import com.enterprise.yetanother.dao.interfaces.UserDao;
import com.enterprise.yetanother.entities.*;
import com.enterprise.yetanother.enums.State;
import com.enterprise.yetanother.enums.Urgency;
import com.enterprise.yetanother.services.interfaces.TicketService;
import com.enterprise.yetanother.test.configuration.PersistenceConfigTest;
import com.enterprise.yetanother.test.configuration.WebAppConfigTest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {
        WebAppConfigTest.class,
        PersistenceConfigTest.class
})
public class TicketServiceTest {

    final static Logger LOGGER = LoggerFactory
                                 .getLogger(TicketServiceTest.class);

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CategoryDao categoryDao;

    private Ticket ticket;
    private User owner;
    private Comment comment;
    private List<Attachment> attachments;

    @Before
    public void setUp() {
        owner = userDao.findById(1L);
        Category category = categoryDao
                            .findByName("APPLICATION_SERVICES");

        comment = new Comment();
        comment.setDate(new Date());
        comment.setText("Cool comment");

        attachments = new ArrayList<>();
        Attachment attachment = new Attachment();
        attachment.setBlob(new byte[]{1,3,4,7});
        attachment.setFileName("attachment.jpg");
        attachments.add(attachment);

        ticket = new Ticket();
        ticket.setName("New name");
        ticket.setState(State.DRAFT);
        ticket.setUrgency(Urgency.CRITICAL);
        ticket.setCategory(category);
    }

    @After
    public void tearDown() {
        ticket = null;
        owner = null;
        comment = null;
        attachments = null;
    }

    @Test
    public void createTicketTest() {
        LOGGER.info("[createTicketTest GO]");

        ticketService.createTicket(ticket, owner, attachments, comment);
        List<Ticket> tickets = ticketDao.findForEmployeeById(1L);
        Ticket createdTicket = tickets.get(tickets.size() - 1);

        Assert.assertEquals(ticket.getName(), createdTicket.getName());
    }

    @Test(expected = AssertionError.class)
    public void createTicketFailedTest() {
        LOGGER.info("[createTicketFailedTest GO]");

        ticket.setName(null);
        ticketService.createTicket(ticket, owner, attachments, comment);
        List<Ticket> tickets = ticketDao.findForEmployeeById(1L);
        Ticket createdTicket = tickets.get(tickets.size() - 1);

        Assert.assertNotNull(createdTicket.getName());
    }

    @Test
    public void findTicketByIdTest() {
        LOGGER.info("[findTicketByIdTest GO]");

        Ticket foundTicket = ticketService.findTicketById(1L);
        Assert.assertEquals(foundTicket.getId().longValue(), 1L);
    }

    @Test(expected = AssertionError.class)
    public void findTicketByIdFailedTest() {
        LOGGER.info("[findTicketByIdFailedTest GO]");

        Ticket foundTicket = ticketService.findTicketById(1L);
        Assert.assertEquals(foundTicket.getId().longValue(), 2L);
    }

    @Test
    public void editTicketTest() {
        LOGGER.info("[editTicketTest GO]");

        Long ticketId = 1L;
        Ticket foundTicket = ticketService.findTicketById(ticketId);
        foundTicket.setDescription("Non empty");
        foundTicket.setUrgency(Urgency.AVERAGE);
        ticketService.editTicket(
                foundTicket, owner, new ArrayList<>(),
                new Comment(), ticketId, owner.getId()
        );
        Ticket editedTicket = ticketService.findTicketById(ticketId);

        Assert.assertEquals("Non empty", editedTicket.getDescription());
        Assert.assertEquals(Urgency.AVERAGE, editedTicket.getUrgency());
    }

    @Test(expected = AssertionError.class)
    public void editTicketFailedTest() {
        LOGGER.info("[editTicketFailedTest GO]");

        Long ticketId = 1L;
        Ticket foundTicket = ticketService.findTicketById(ticketId);
        foundTicket.setUrgency(Urgency.AVERAGE);
        ticketService.editTicket(
                foundTicket, owner, new ArrayList<>(), new Comment(),
                ticketId, owner.getId()
        );
        Ticket editedTicket = ticketService.findTicketById(ticketId);

        Assert.assertEquals(Urgency.LOW, editedTicket.getUrgency());
    }

    @Test
    public void editStateTest() {
        LOGGER.info("[editStateTest GO]");

        Long ticketId = 1L;
        ticketService.editState(
                ticketId, "Cancel", State.DRAFT, owner.getEmail()
        );
        Ticket editedTicket = ticketService.findTicketById(ticketId);

        Assert.assertEquals(State.CANCELED, editedTicket.getState());
    }

    @Test(expected = AssertionError.class)
    public void editStateFailedTest() {
        LOGGER.info("[editStateFailedTest GO]");

        Long ticketId = 1L;
        ticketService.editState(
                ticketId, "Cancel", State.DRAFT, owner.getEmail()
        );
        Ticket editedTicket = ticketService.findTicketById(ticketId);

        Assert.assertEquals(State.NEW, editedTicket.getState());
    }
}
