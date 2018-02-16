package com.enterprise.yetanother.dao.interfaces;

import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.entities.User;
import com.enterprise.yetanother.enums.State;
import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.entities.User;
import com.enterprise.yetanother.enums.State;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *@author andrey
 */
@Repository
public interface TicketDao {

    void create(Ticket ticket);
    List<Ticket> findForEngineerById(Long assigneeId);
    List<Ticket> findForEmployeeById(Long ownerId);
    List<Ticket> findForManagerById(Long approverId);
    Ticket findTicketById(Long id);
    void update(Ticket ticket);
    void setState(Ticket ticket, State state);
    void approve(Ticket ticket, State state, User user);
    void assign(Ticket ticket, State state, User user);
    void persist(Ticket ticket);
    void saveOrUpdate(Ticket ticket);
}
