package com.enterprise.yetanother.services.implementations;

import com.enterprise.yetanother.dao.interfaces.UserDao;
import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.entities.User;
import com.enterprise.yetanother.enums.Roles;
import com.enterprise.yetanother.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *@author andrey
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User get(Long id) {
        User user;
        if (id != null) {
            user = userDao.findById(id);
        } else {
            user = null;
        }
        if (user != null) {
            return user;
        } else {
            return null;
        }
    }

    @Override
    public void create(User user) {
        if (user != null) {
            userDao.create(user);
        }
    }

    @Override
    public User getUserByEmail(String email) {
        User user;
        if (email != null) {
            user = userDao.findByEmail(email);
        } else {
            user = null;
        }
        if (user != null) {
            return user;
        } else {
            return null;
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public String getUserLogin() {

        String userLogin;
        Object currentUser = SecurityContextHolder.getContext()
                             .getAuthentication().getPrincipal();
        if (currentUser instanceof UserDetails) {
            userLogin = ((UserDetails) currentUser).getUsername();
        } else {
            userLogin = currentUser.toString();
        }
        return userLogin;
    }

    @Override
    public List<User> getAllManagers() {
        return userDao.getAllByRole(Roles.MANAGER);
    }

    @Override
    public List<User> getAllEngineers() {
        return userDao.getAllByRole(Roles.ENGINEER);
    }

    @Override
    public User getCreator(Ticket ticket) {
        if (ticket != null) {
            return userDao.getCreator(ticket);
        } else {
            return null;
        }
    }

    @Override
    public User getApprover(Ticket ticket) {
        if (ticket != null) {
            return userDao.getApprover(ticket);
        } else {
            return null;
        }
    }

    @Override
    public User getAssignee(Ticket ticket) {
        if (ticket != null) {
            return userDao.getAssignee(ticket);
        } else {
            return null;
        }
    }
}