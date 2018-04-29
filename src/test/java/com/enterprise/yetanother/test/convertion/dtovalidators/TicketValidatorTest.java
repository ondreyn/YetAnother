package com.enterprise.yetanother.test.convertion.dtovalidators;

import com.enterprise.yetanother.dao.interfaces.CategoryDao;
import com.enterprise.yetanother.dao.interfaces.TicketDao;
import com.enterprise.yetanother.dto.ticket.TicketDto;
import com.enterprise.yetanother.entities.Category;
import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.enums.State;
import com.enterprise.yetanother.enums.Urgency;
import com.enterprise.yetanother.test.configuration.PersistenceConfigTest;
import com.enterprise.yetanother.test.configuration.WebAppConfigTest;
import com.enterprise.yetanother.convertion.dtovalidation.interfaces.TicketValidator;
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
import java.util.List;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {
        WebAppConfigTest.class,
        PersistenceConfigTest.class
})
public class TicketValidatorTest {

    final static Logger LOGGER = LoggerFactory
                                 .getLogger(TicketValidatorTest.class);

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private TicketValidator ticketValidator;

    @Autowired
    private CategoryDao categoryDao;

    private List<Ticket> nonValidTickets;
    private List<TicketDto> nonValidTicketDtos;
    private Category category;

    @Before
    public void setUp() {
        category = categoryDao.findByName("APPLICATION_SERVICES");
    }

    @Test
    public void validateEntityTest() {
        LOGGER.info("[validateEntityTest GO]");

        nonValidTickets = new ArrayList<>();

        Ticket ticket1 = ticketDao.findTicketById(1L);
        ticket1.setState(null);
        nonValidTickets.add(ticket1);

        Ticket ticket2 = ticketDao.findTicketById(1L);
        ticket2.setCategory(new Category());
        nonValidTickets.add(ticket2);

        Ticket ticket3 = ticketDao.findTicketById(1L);
        ticket3.setName("");
        nonValidTickets.add(ticket3);

        Ticket ticket4 = ticketDao.findTicketById(1L);
        ticket4.setUrgency(null);
        nonValidTickets.add(ticket4);

        Ticket ticket5 = ticketDao.findTicketById(1L);
        ticket5.setDescription("Привет");
        nonValidTickets.add(ticket5);

        for (Ticket ticket: nonValidTickets) {
            Assert.assertFalse(ticketValidator.validate(ticket));
        }
    }

    @Test(expected = AssertionError.class)
    public void validateStateFailedTest() {
        LOGGER.info("[validateStateFailedTest GO]");

        Ticket ticket = ticketDao.findTicketById(1L);
        ticket.setState(null);
        ticket.setCategory(category);
        ticket.setName("Name");
        ticket.setUrgency(Urgency.CRITICAL);
        ticket.setDescription("Hi");

        Assert.assertTrue(ticketValidator.validate(ticket));
    }

    @Test(expected = AssertionError.class)
    public void validateCategoryFailedTest() {
        LOGGER.info("[validateCategoryFailedTest GO]");

        Ticket ticket = ticketDao.findTicketById(1L);
        ticket.setState(State.DRAFT);
        ticket.setCategory(new Category());
        ticket.setName("Name");
        ticket.setUrgency(Urgency.CRITICAL);
        ticket.setDescription("Hi");

        Assert.assertTrue(ticketValidator.validate(ticket));
    }

    @Test(expected = AssertionError.class)
    public void validateNameFailedTest() {
        LOGGER.info("[validateNameFailedTest GO]");

        Ticket ticket = ticketDao.findTicketById(1L);
        ticket.setState(State.DRAFT);
        ticket.setCategory(category);
        ticket.setName("");
        ticket.setUrgency(Urgency.CRITICAL);
        ticket.setDescription("Hi");

        Assert.assertTrue(ticketValidator.validate(ticket));
    }

    @Test(expected = AssertionError.class)
    public void validateUrgencyFailedTest() {
        LOGGER.info("[validateUrgencyFailedTest GO]");

        Ticket ticket = ticketDao.findTicketById(1L);
        ticket.setState(State.DRAFT);
        ticket.setCategory(category);
        ticket.setName("Name");
        ticket.setUrgency(null);
        ticket.setDescription("Hi");

        Assert.assertTrue(ticketValidator.validate(ticket));
    }

    @Test(expected = AssertionError.class)
    public void validateDescriptionFailedTest() {
        LOGGER.info("[validateDescriptionFailedTest GO]");

        Ticket ticket = ticketDao.findTicketById(1L);
        ticket.setState(State.DRAFT);
        ticket.setCategory(category);
        ticket.setName("Name");
        ticket.setUrgency(Urgency.CRITICAL);
        ticket.setDescription("Привет");

        Assert.assertTrue(ticketValidator.validate(ticket));
    }

    @Test
    public void validateDtoTest() {
        LOGGER.info("[validateDtoTest GO]");

        nonValidTicketDtos = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            TicketDto ticketDto = new TicketDto();
            ticketDto.setName("a");
            ticketDto.setCategory(category);
            ticketDto.setState(State.DRAFT);
            ticketDto.setUrgency(Urgency.HIGH);
            ticketDto.setDescription("d");
            nonValidTicketDtos.add(ticketDto);
        }

        nonValidTicketDtos.get(0).setState(null);
        nonValidTicketDtos.get(1).setCategory(new Category());
        nonValidTicketDtos.get(2).setName("");
        nonValidTicketDtos.get(3).setUrgency(null);
        nonValidTicketDtos.get(4).setDescription("Привет");

        for (TicketDto ticketDto: nonValidTicketDtos) {
            Assert.assertFalse(ticketValidator.validate(ticketDto));
        }
    }

    @Test(expected = AssertionError.class)
    public void validateDtoFailedTest() {
        LOGGER.info("[validateDtoFailedTest GO]");

        TicketDto ticketDto = new TicketDto();
        ticketDto.setName("a");
        ticketDto.setCategory(category);
        ticketDto.setState(State.DRAFT);
        ticketDto.setUrgency(Urgency.HIGH);
        ticketDto.setDescription("d");

        Assert.assertFalse(ticketValidator.validate(ticketDto));
    }
}