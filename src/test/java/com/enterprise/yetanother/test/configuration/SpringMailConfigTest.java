package com.enterprise.yetanother.test.configuration;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

/**
 *@author andrey
 */
@Configuration
@PropertySource("classpath:mailbox.properties")
public class SpringMailConfigTest implements ApplicationContextAware {

    final static Logger LOGGER = LoggerFactory
                                 .getLogger(SpringMailConfigTest.class);

    private static final String JAVA_MAIL_FILE = "classpath:javamail.properties";

    @Value("${mail.server.host}")
    private String HOST;

    @Value("${mail.server.port}")
    private Integer PORT;

    @Value("${mail.server.protocol}")
    private String PROTOCOL;

    @Value("${mail.server.username}")
    private String USERNAME;

    @Value("${mail.server.password}")
    private String PASSWORD;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(
            final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean
    public JavaMailSender mailSender() throws IOException {
        LOGGER.info("[JavaMailSender Bean]");

        final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(HOST);
        mailSender.setPort(PORT);
        mailSender.setProtocol(PROTOCOL);
        mailSender.setUsername(USERNAME);
        mailSender.setPassword(PASSWORD);
        final Properties javaMailProperties = new Properties();
        javaMailProperties.load(this.applicationContext
                                .getResource(JAVA_MAIL_FILE).getInputStream());
        mailSender.setJavaMailProperties(javaMailProperties);

        return mailSender;
    }

    @Bean(name = "mailResolver")
    public SpringResourceTemplateResolver mailTemplateResolver() {
        LOGGER.info("[SpringResourceTemplateResolver 'mailResolver' Bean]");

        final SpringResourceTemplateResolver templateResolver =
                                        new SpringResourceTemplateResolver();
        templateResolver.setPrefix("/WEB-INF/mail/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(true);

        return templateResolver;
    }

    @Bean(name = "emailEngine")
    public SpringTemplateEngine templateEngine(){
        LOGGER.info("[SpringTemplateEngine 'emailEngine' Bean]");

        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(mailTemplateResolver());

        return templateEngine;
    }
}
