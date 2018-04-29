package com.enterprise.yetanother.services.interfaces;

import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.entities.User;

import javax.mail.MessagingException;
import java.util.List;

/**
 *@author andrey
 */
public interface EmailService {

    void sendBroadcastMail(final Ticket ticket, List<User> recipients, String[]
                           mailOptions) throws MessagingException;

    void sendPersonalMail(final Ticket ticket, User recipients, String[]
                          mailOptions) throws MessagingException;
    
    boolean isEnabled();
}
