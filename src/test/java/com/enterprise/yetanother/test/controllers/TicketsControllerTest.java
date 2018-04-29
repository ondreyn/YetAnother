package com.enterprise.yetanother.test.controllers;

import com.enterprise.yetanother.dto.ticket.TicketDto;
import com.enterprise.yetanother.entities.User;
import com.enterprise.yetanother.enums.Roles;
import com.enterprise.yetanother.enums.State;
import com.enterprise.yetanother.enums.Urgency;
import com.enterprise.yetanother.test.configuration.PersistenceConfigTest;
import com.enterprise.yetanother.test.configuration.WebAppConfigTest;
import com.enterprise.yetanother.controllers.tickets.TicketsController;
/*import com.enterprise.yetanother.dto.ticket.TicketDto;
import com.enterprise.yetanother.entities.User;
import com.enterprise.yetanother.enums.Roles;
import com.enterprise.yetanother.enums.State;
import com.enterprise.yetanother.enums.Urgency;
import com.enterprise.yetanother.test.configuration.PersistenceConfigTest;
import com.enterprise.yetanother.test.configuration.WebAppConfigTest;*/
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {
        WebAppConfigTest.class, PersistenceConfigTest.class
})
public class TicketsControllerTest {

    final static Logger LOGGER = LoggerFactory
                                 .getLogger(TicketsControllerTest.class);

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    @Qualifier("TicketsControllerMock")
    private TicketsController ticketsController;

    private User user;
    private TicketDto ticket1;
    private TicketDto ticket2;

    @Before
    public void setup() {
        Mockito.reset(ticketsController);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

        user = new User();
        user.setId(1L);
        user.setEmail("user1_mogilev@yopmail.com");
        user.setRole(Roles.EMPLOYEE);

        ticket1 = new TicketDto();
        ticket1.setId(1L);
        ticket1.setState(State.NEW);
        ticket1.setUrgency(Urgency.HIGH);
        ticket1.setName("FirstTicket");

        ticket2 = new TicketDto();
        ticket2.setId(2L);
        ticket2.setState(State.DRAFT);
        ticket2.setUrgency(Urgency.CRITICAL);
        ticket2.setName("SecondTicket");

        given(this.ticketsController.getAllAccessibleTickets())
                  .willReturn(Arrays.asList(ticket1, ticket2));
    }

    @Test
    public void getAllTicketsTest() throws Exception {
        LOGGER.info("[getAllTicketsTest GO]");

        mockMvc.perform(get("/tickets")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(content().string("[ {\n" +
                        "  \"id\" : 1,\n" +
                        "  \"category\" : null,\n" +
                        "  \"name\" : \"FirstTicket\",\n" +
                        "  \"description\" : null,\n" +
                        "  \"urgency\" : \"HIGH\",\n" +
                        "  \"createdOn\" : null,\n" +
                        "  \"desiredResolutionDate\" : null,\n" +
                        "  \"state\" : \"NEW\",\n" +
                        "  \"assignee\" : null,\n" +
                        "  \"owner\" : null,\n" +
                        "  \"approver\" : null,\n" +
                        "  \"feedback\" : null" +
                        "\n}, {\n" +
                        "  \"id\" : 2,\n" +
                        "  \"category\" : null,\n" +
                        "  \"name\" : \"SecondTicket\",\n" +
                        "  \"description\" : null,\n" +
                        "  \"urgency\" : \"CRITICAL\",\n" +
                        "  \"createdOn\" : null,\n" +
                        "  \"desiredResolutionDate\" : null,\n" +
                        "  \"state\" : \"DRAFT\",\n" +
                        "  \"assignee\" : null,\n" +
                        "  \"owner\" : null,\n" +
                        "  \"approver\" : null,\n" +
                        "  \"feedback\" : null" +
                        "\n} ]"));

        verify(ticketsController, times(1))
                                                    .getAllAccessibleTickets();
        verifyNoMoreInteractions(ticketsController);
    }
}
