package com.enterprise.yetanother.dao.implementations;

import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.entities.User;
import com.enterprise.yetanother.enums.Roles;
import com.enterprise.yetanother.enums.State;
import com.enterprise.yetanother.dao.interfaces.TicketDao;
import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.entities.User;
import com.enterprise.yetanother.enums.Roles;
import com.enterprise.yetanother.enums.State;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *@author andrey
 */
@Repository
public class TicketDaoImpl implements TicketDao {

    final static Logger LOGGER = LoggerFactory.getLogger(TicketDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public void create(Ticket ticket) {
        if (ticket != null) {
            sessionFactory.getCurrentSession().save(ticket);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ticket> findForEngineerById(Long assigneeId) {
        if (assigneeId != null) {
            String hql = "SELECT e FROM Ticket e WHERE e.state = :APPROVED " +
                         "OR (e.assignee.id = :assigneeId AND e.state IN " +
                         "(:IN_PROGRESS, :DONE)) ORDER BY e" +
                         ".desiredResolutionDate ASC";
            Query<Ticket> query = sessionFactory.getCurrentSession()
                                  .createQuery(hql, Ticket.class);
            query.setParameter("APPROVED", State.APPROVED);
            query.setParameter("assigneeId", assigneeId);
            query.setParameter("IN_PROGRESS", State.IN_PROGRESS);
            query.setParameter("DONE", State.DONE);

            return query.list();
        } else {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ticket> findForEmployeeById(Long ownerId) {
        if (ownerId != null) {
            String hql = "SELECT e FROM Ticket e WHERE e.owner.id = :ownerId " +
                         "ORDER BY e.desiredResolutionDate ASC";
            Query<Ticket> query = sessionFactory.getCurrentSession()
                                  .createQuery(hql, Ticket.class);
            query.setParameter("ownerId", ownerId);
            return query.list();
        } else {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ticket> findForManagerById(Long approverId) {
        if (approverId != null) {
            String hql = "SELECT e FROM Ticket e WHERE " +
                         "(e.approver.id = :approverId AND e.state IN " +
                         "(:APPROVED, :DECLINED, :CANCELED, :IN_PROGRESS, " +
                         ":DONE)) OR (e.owner.id = :approverId) " +
                         "OR (e.state = :NEW AND e.owner.id IN (SELECT a " +
                         "FROM User a WHERE a.role = :Role)) " +
                         "ORDER BY e.desiredResolutionDate ASC";

            Query<Ticket> query = sessionFactory.getCurrentSession()
                                  .createQuery(hql, Ticket.class);
            query.setParameter("approverId", approverId);
            query.setParameter("APPROVED", State.APPROVED);
            query.setParameter("DECLINED", State.DECLINED);
            query.setParameter("CANCELED", State.CANCELED);
            query.setParameter("IN_PROGRESS", State.IN_PROGRESS);
            query.setParameter("DONE", State.DONE);
            query.setParameter("NEW", State.NEW);
            query.setParameter("Role", Roles.valueOf("EMPLOYEE"));
            return query.list();
        } else {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Ticket findTicketById(Long id) {
        if (id != null) {
            String hql = "SELECT e FROM Ticket e WHERE e.id = :id";
            Query<Ticket> query = sessionFactory.getCurrentSession()
                                  .createQuery(hql, Ticket.class);
            query.setParameter("id", id);
            return query.uniqueResult();
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public void update(Ticket ticket) {
        Ticket toUpdate = findTicketById(ticket.getId());
        try {
            if (ticket.getName() != null) {
                if (!ticket.getName().isEmpty()) {
                    toUpdate.setName(ticket.getName());
                }
            }
            if (ticket.getDescription() != null) {
                if (!ticket.getDescription().isEmpty()) {
                    toUpdate.setDescription(ticket.getDescription());
                }
            }
            if (ticket.getCategory() != null) {
                toUpdate.setCategory(ticket.getCategory());
            }
            if (ticket.getUrgency() != null) {
                toUpdate.setUrgency(ticket.getUrgency());
            }
            if (ticket.getState() != null) {
                toUpdate.setState(ticket.getState());
            }
            if (ticket.getDesiredResolutionDate() != null) {
                toUpdate.setDesiredResolutionDate(ticket
                        .getDesiredResolutionDate());
            }
        } catch (Exception e) {
            LOGGER.error("[update: Exception thrown!]", e);
            return;
        }

        sessionFactory.getCurrentSession().update(toUpdate);
    }

    @Override
    @Transactional
    public void setState(Ticket ticket, State state) {
        LOGGER.info("[setState GO]");

        if (ticket.getState() != state) {
            ticket.setState(state);
            sessionFactory.getCurrentSession().update(ticket);
        }
    }

    @Override
    @Transactional
    public void approve(Ticket ticket, State state, User user) {
        LOGGER.info("[approve GO]");

        if (ticket.getState() != state) {
            ticket.setState(state);
            ticket.setApprover(user);
            sessionFactory.getCurrentSession().update(ticket);
        }
    }

    @Override
    @Transactional
    public void assign(Ticket ticket, State state, User user) {
        LOGGER.info("[assign GO]");

        if (ticket.getState() != state) {
            ticket.setState(state);
            ticket.setAssignee(user);
            sessionFactory.getCurrentSession().update(ticket);
        }
    }

    @Override
    @Transactional
    public void persist(Ticket ticket) {
        sessionFactory.getCurrentSession().persist(ticket);
    }

    @Override
    @Transactional
    public void saveOrUpdate(Ticket ticket) {
        sessionFactory.getCurrentSession().saveOrUpdate(ticket);
    }
}
