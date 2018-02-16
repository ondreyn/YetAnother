package com.enterprise.yetanother.services.interfaces;

import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *@author andrey
 */
@Service
public interface UserService {

    User get(Long id);
    void create(User user);
    User getUserByEmail(String email);
    List<User> getAllUsers();
    String getUserLogin();
    List<User> getAllManagers();
    List<User> getAllEngineers();
    User getCreator(Ticket ticket);
    User getApprover(Ticket ticket);
    User getAssignee(Ticket ticket);
}
