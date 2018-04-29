package com.enterprise.yetanother.dao.interfaces;

import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.entities.User;
import com.enterprise.yetanother.enums.Roles;
/*import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.entities.User;
import com.enterprise.yetanother.enums.Roles;*/
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *@author andrey
 */
@Repository
public interface UserDao {

    void create(User user);
    List<User> getAllUsers();
    User findById(Long id);
    User findByEmail(String email);
    void persist(User user);
    void update(User user);
    void saveOrUpdate(User user);
    List<User> getAllByRole(Roles role);
    User getCreator(Ticket ticket);
    User getApprover(Ticket ticket);
    User getAssignee(Ticket ticket);
}
