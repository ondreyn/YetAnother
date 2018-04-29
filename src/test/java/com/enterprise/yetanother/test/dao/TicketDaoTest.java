package com.enterprise.yetanother.test.dao;

import com.enterprise.yetanother.dao.interfaces.CategoryDao;
import com.enterprise.yetanother.dao.interfaces.TicketDao;
import com.enterprise.yetanother.dao.interfaces.UserDao;
import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.entities.User;
import com.enterprise.yetanother.enums.State;
import com.enterprise.yetanother.enums.Urgency;
import com.enterprise.yetanother.test.configuration.PersistenceConfigTest;
import com.enterprise.yetanother.test.configuration.WebAppConfigTest;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {
        WebAppConfigTest.class,
        PersistenceConfigTest.class
})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TicketDaoTest {

    final static Logger LOGGER = LoggerFactory.getLogger(TicketDaoTest.class);

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private UserDao userDao;

    private Ticket ticket;
    private Ticket newTicket;
    private List<Ticket> allTickets;
    private int ticketsQuantity;

    @Test
    public void test01FindAllTicketsByEmployeeIdTest() {
        LOGGER.info("[test01FindAllTicketsByEmployeeIdTest GO]");

        allTickets = ticketDao.findForEmployeeById(1L);
        ticketsQuantity = allTickets.size();

        Assert.assertEquals(ticketsQuantity, allTickets.size());
    }

    @Test
    public void test04FindTicketByIdTest() {
        LOGGER.info("[test04FindTicketByIdTest GO]");

        Long id = 1L;
        ticket = ticketDao.findTicketById(id);

        Assert.assertEquals(ticket.getId(), id);
    }

    @Test
    public void test02CreateTest() {
        LOGGER.info("[test02CreateTest GO]");

        allTickets = ticketDao.findForEmployeeById(1L);
        ticketsQuantity = allTickets.size();
        newTicket = new Ticket();
        newTicket.setCategory(categoryDao.findByName("HARDWARE_SOFTWARE"));
        newTicket.setName("A new name");
        newTicket.setUrgency(Urgency.CRITICAL);
        newTicket.setState(State.NEW);
        newTicket.setOwner(userDao.findById(1L));
        ticketDao.create(newTicket);
        allTickets = ticketDao.findForEmployeeById(1L);

        Assert.assertEquals(ticketsQuantity + 1, allTickets.size());
    }

    @Test(expected = AssertionError.class)
    public void test03CreateFailedTest() {
        LOGGER.info("[test03CreateFailedTest GO]");

        allTickets = ticketDao.findForEmployeeById(1L);
        ticketsQuantity = allTickets.size();
        newTicket = new Ticket();
        ticketDao.create(newTicket);
        allTickets = ticketDao.findForEmployeeById(1L);

        Assert.assertEquals(ticketsQuantity + 1, allTickets.size());
    }

    @Test
    public void test05UpdateTest() {
        LOGGER.info("[test05UpdateTest GO]");

        ticket = ticketDao.findTicketById(1L);
        ticket.setName("Renamed");
        ticketDao.update(ticket);
        newTicket = ticketDao.findTicketById(1L);

        Assert.assertEquals("Renamed", newTicket.getName());
    }

    @Test(expected = AssertionError.class)
    public void test06UpdateFailedTest() {
        LOGGER.info("[test06UpdateFailedTest GO]");

        ticket = ticketDao.findTicketById(1L);
        String previousName = ticket.getName();
        ticket.setName("Another");
        ticketDao.update(ticket);
        newTicket = ticketDao.findTicketById(1L);
        LOGGER.info("[newTicket.getName(): " + newTicket.getName());

        Assert.assertEquals(previousName, newTicket.getName());
    }

    @Test
    public void test07SetStateTest() {
        LOGGER.info("[test07SetStateTest GO]");

        ticket = ticketDao.findTicketById(1L);
        ticketDao.setState(ticket, State.CANCELED);
        newTicket = ticketDao.findTicketById(1L);

        Assert.assertEquals(State.CANCELED, newTicket.getState());
    }

    @Test(expected = AssertionError.class)
    public void test08SetStateFailedTest() {
        LOGGER.info("[test08SetStateFailedTest GO]");

        ticket = ticketDao.findTicketById(1L);
        ticketDao.setState(ticket, State.CANCELED);
        newTicket = ticketDao.findTicketById(1L);

        Assert.assertEquals(State.DONE, newTicket.getState());
    }

    @Test
    public void test09ApproveTest() {
        LOGGER.info("[test09ApproveTest GO]");

        User approver = userDao.findById(4L);
        ticket = ticketDao.findTicketById(1L);
        ticketDao.approve(ticket, State.APPROVED, approver);
        newTicket = ticketDao.findTicketById(1L);

        Assert.assertEquals(approver.getId(), newTicket.getApprover().getId());
    }

    @Test(expected = AssertionError.class)
    public void test10ApproveFailedTest() {
        LOGGER.info("[test10ApproveFailedTest GO]");

        User approver = userDao.findById(2L);
        ticket = ticketDao.findTicketById(1L);
        ticketDao.approve(ticket, State.APPROVED, approver);
        newTicket = ticketDao.findTicketById(1L);

        Assert.assertEquals(approver.getId(), newTicket.getApprover().getId());
    }

    @Test
    public void test11AssignTest() {
        LOGGER.info("[test11AssignTest GO]");

        User assignee = userDao.findById(5L);
        ticket = ticketDao.findTicketById(1L);
        ticketDao.assign(ticket, State.IN_PROGRESS, assignee);
        newTicket = ticketDao.findTicketById(1L);

        Assert.assertEquals(assignee.getId(), newTicket.getAssignee().getId());
    }

    @Test(expected = AssertionError.class)
    public void test12AssignFailedTest() {
        LOGGER.info("[test12AssignFailedTest GO]");

        User assignee = userDao.findById(3L);
        ticket = ticketDao.findTicketById(1L);
        ticketDao.assign(ticket, State.IN_PROGRESS, assignee);
        newTicket = ticketDao.findTicketById(1L);

        Assert.assertEquals(assignee.getId(), newTicket.getAssignee().getId());
    }
}
