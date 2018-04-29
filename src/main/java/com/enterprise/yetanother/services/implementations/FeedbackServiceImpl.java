package com.enterprise.yetanother.services.implementations;

import com.enterprise.yetanother.dao.interfaces.FeedbackDao;
import com.enterprise.yetanother.entities.Feedback;
import com.enterprise.yetanother.entities.User;
import com.enterprise.yetanother.init.Properties;
import com.enterprise.yetanother.services.interfaces.EmailService;
import com.enterprise.yetanother.services.interfaces.FeedbackService;
import com.enterprise.yetanother.services.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;

/**
 *@author andrey
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {

    final static Logger LOGGER = LoggerFactory
                                 .getLogger(FeedbackServiceImpl.class);
    @Autowired
    private FeedbackDao feedbackDao;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Override
    public void create(Feedback feedback) {
        if (feedback != null) {
            feedbackDao.create(feedback);
            if (emailService.isEnabled()) {
            	try {
            		User engineer = userService.getAssignee(feedback.getTicket());
            		emailService.sendPersonalMail(feedback.getTicket(), engineer,
                                              Properties.FEEDBACK_PROVIDED);
            	} catch (MessagingException e) {
            		LOGGER.error("[create: MessagingException]", e);
            	}
            }
        }
    }

    @Override
    public Feedback getFeedback(Long ticket_id) {
        if (ticket_id != null) {
            return feedbackDao.findByTicket(ticket_id);
        } else {
            return null;
        }
    }

    @Override
    public boolean doesExistFeedback(Long ticket_id) {
        return ticket_id != null && feedbackDao.findByTicket(ticket_id) != null;
    }

    @Override
    public List<Feedback> getAllFeedback() {
        return feedbackDao.findAllTickets();
    }
}