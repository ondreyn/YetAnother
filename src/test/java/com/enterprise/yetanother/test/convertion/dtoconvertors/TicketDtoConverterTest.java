package com.enterprise.yetanother.test.convertion.dtoconvertors;

import com.enterprise.yetanother.dao.interfaces.CategoryDao;
import com.enterprise.yetanother.dao.interfaces.TicketDao;
import com.enterprise.yetanother.dto.ticket.TicketDto;
import com.enterprise.yetanother.entities.Category;
import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.enums.State;
import com.enterprise.yetanother.enums.Urgency;
import com.enterprise.yetanother.test.configuration.PersistenceConfigTest;
import com.enterprise.yetanother.test.configuration.WebAppConfigTest;
import com.enterprise.yetanother.convertion.dtoconverters.interfaces.TicketDtoConverter;
/*import com.enterprise.yetanother.dao.interfaces.CategoryDao;
import com.enterprise.yetanother.dao.interfaces.TicketDao;
import com.enterprise.yetanother.dto.ticket.TicketDto;
import com.enterprise.yetanother.entities.Category;
import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.enums.State;
import com.enterprise.yetanother.enums.Urgency;
import com.enterprise.yetanother.test.configuration.PersistenceConfigTest;
import com.enterprise.yetanother.test.configuration.WebAppConfigTest;*/
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

import java.util.List;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {
        WebAppConfigTest.class,
        PersistenceConfigTest.class
})
public class TicketDtoConverterTest {

    final static Logger LOGGER = LoggerFactory
                                 .getLogger(TicketDtoConverterTest.class);

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private TicketDtoConverter ticketDtoConverter;

    private TicketDto ticketDto;

    @Before
    public void setUp() {
        Category category = categoryDao
                            .findByName("APPLICATION_SERVICES");
        ticketDto = new TicketDto();
        ticketDto.setName("a");
        ticketDto.setCategory(category);
        ticketDto.setState(State.DRAFT);
        ticketDto.setUrgency(Urgency.HIGH);
        ticketDto.setDescription("d");
    }

    @Test(expected = AssertionError.class)
    public void dtoToEntityTest() {
        LOGGER.info("[dtoToEntityTest GO]");

        Ticket ticket = ticketDtoConverter.dtoToEntity(ticketDto);
        Assert.assertNull(ticket);
    }

    @Test(expected = AssertionError.class)
    public void entityToDtoTest() {
        LOGGER.info("[entityToDtoTest GO]");

        TicketDto ticketDto = ticketDtoConverter.entityToDto(ticketDao
                                                .findTicketById(1L));
        Assert.assertNull(ticketDto);
    }

    @Test(expected = AssertionError.class)
    public void entitiesToDtosTest() {
        LOGGER.info("[entitiesToDtosTest GO]");

        List<TicketDto> ticketDtos = ticketDtoConverter.entitiesToDtos(ticketDao
                                     .findForEmployeeById(1L));
        Assert.assertNull(ticketDtos.get(0));
    }
}
