package com.enterprise.yetanother.services.implementations;

import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.entities.User;
import com.enterprise.yetanother.services.interfaces.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;

/**
 *@author andrey
 */
@Service
@PropertySource("classpath:mailbox.properties")
public class EmailServiceImpl implements EmailService {

    final static Logger LOGGER = LoggerFactory
                                 .getLogger(EmailServiceImpl.class);
    
    @Value("${mail.enable}")
    private boolean ENABLED;

    @Value("${mail.server.username}")
    private String SENDER;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    @Qualifier("emailEngine")
    private TemplateEngine htmlTemplateEngine;
    
    @Override
    public boolean isEnabled() {
    	return ENABLED;    	
    }

    private List<MimeMessageHelper> broadcastMail(List<User> users,
                                                  String subject) {
        List<MimeMessageHelper> mailList = new ArrayList<>();
        try {
            for (User user : users) {
                final MimeMessage mimeMessage = this.mailSender
                                                .createMimeMessage();
                final MimeMessageHelper message =
                        new MimeMessageHelper(mimeMessage, "UTF-8");
                message.setSubject(subject);
                message.setFrom(SENDER);
                message.setTo(user.getEmail());
                mailList.add(message);
            }
            return mailList;
        } catch (MessagingException e) {
            LOGGER.error("[broadcastMail: MessagingException]", e);
            return null;
        }
    }

    @Override
    public void sendBroadcastMail(Ticket ticket, List<User> recipients,
                                  String[] mailOptions) throws MessagingException {    	
    	
        final Context ctx   = new Context();
        ctx.setVariable("ticket_id", ticket.getId());

        List<MimeMessageHelper> mailList = broadcastMail(recipients,
                                                         mailOptions[0]);
        if (mailList != null) {
            for (MimeMessageHelper message : mailList) {
                String textContent = this.htmlTemplateEngine
                                         .process(mailOptions[1], ctx);
                message.setText(textContent, true);
                this.mailSender.send(message.getMimeMessage());
            }
        } else {
            LOGGER.warn("[sendBroadcastMail: found NO recipients!]");
            return;
        }

        LOGGER.info(String.format("[sendBroadcastMail: %d mails '%s' SENT]",
                    mailList.size(), mailOptions[1]));
    }

    @Override
    public void sendPersonalMail(Ticket ticket, User recipient,
                                 String[] mailOptions) throws MessagingException {
    	
        if (ticket != null && recipient != null && mailOptions != null) {
            final Context ctx = new Context();

            ctx.setVariable("ticket_id", ticket.getId());
            ctx.setVariable("recipientFirstName", recipient.getFirstName());
            ctx.setVariable("recipientLastName", recipient.getLastName());

            final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            final MimeMessageHelper message =
                    new MimeMessageHelper(mimeMessage, "UTF-8");
            message.setSubject(mailOptions[0]);
            message.setFrom(SENDER);
            message.setTo(recipient.getEmail());
            String textContent = this.htmlTemplateEngine
                                     .process(mailOptions[1], ctx);
            message.setText(textContent, true);
            this.mailSender.send(mimeMessage);

            LOGGER.info(String.format("[sendPersonalMail: '%s' SENT]",
                        mailOptions[1]));
        } else {
            LOGGER.info("[sendPersonalMail: some income parameters are nulls!]");
        }
    }
}