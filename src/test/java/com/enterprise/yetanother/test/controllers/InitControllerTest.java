package com.enterprise.yetanother.test.controllers;

import com.enterprise.yetanother.test.configuration.PersistenceConfigTest;
import com.enterprise.yetanother.test.configuration.WebAppConfigTest;
import com.gargoylesoftware.htmlunit.TextPage;
import com.gargoylesoftware.htmlunit.WebClient;
import com.enterprise.yetanother.controllers.InitController;
import com.enterprise.yetanother.test.configuration.PersistenceConfigTest;
import com.enterprise.yetanother.test.configuration.WebAppConfigTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {
        WebAppConfigTest.class, PersistenceConfigTest.class
})
public class InitControllerTest {

    final static Logger LOGGER = LoggerFactory
                                 .getLogger(InitControllerTest.class);

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private InitController initController;

    private WebClient webClient;

    @Before
    public void setup() {
        Mockito.reset(initController);

        this.webClient = MockMvcWebClientBuilder
                .webAppContextSetup(wac)
                .build();
    }

    @Test
    public void getAllPages() throws Exception {
        LOGGER.info("[getTicketsPage GO]");

        TextPage createPage = webClient
                              .getPage("http://localhost/ticketsPage");
        Assert.assertNotNull("page has content", createPage.getContent());

        createPage = webClient.getPage("http://localhost/ticketCreatePage");
        Assert.assertNotNull("page has content", createPage.getContent());

        createPage = webClient
                     .getPage("http://localhost/ticketOverviewPage/1");
        Assert.assertNotNull("page has content", createPage.getContent());

        createPage = webClient.getPage("http://localhost/ticketEditPage/1");
        Assert.assertNotNull("page has content", createPage.getContent());

        createPage = webClient
                     .getPage("http://localhost/ticketFeedbackPage/1/new");
        Assert.assertNotNull("page has content", createPage.getContent());
    }
}