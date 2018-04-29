package com.enterprise.yetanother.dao.implementations;

import com.enterprise.yetanother.entities.Feedback;
import com.enterprise.yetanother.dao.interfaces.FeedbackDao;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *@author andrey
 */
@Repository
public class FeedbackDaoImpl implements FeedbackDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public void create(Feedback feedback) {
        if (feedback != null) {
            sessionFactory.getCurrentSession().save(feedback);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Feedback findByTicket(Long ticketId) {
        if (ticketId != null) {
            String hql = "SELECT e FROM Feedback e WHERE e.ticket.id = " +
                                                           ticketId;
            Query<Feedback> query = sessionFactory.getCurrentSession()
                                    .createQuery(hql, Feedback.class);
            return query.uniqueResult();
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public void persist(Feedback feedback) {
        sessionFactory.getCurrentSession().persist(feedback);
    }

    @Override
    @Transactional
    public void update(Feedback feedback) {
        sessionFactory.getCurrentSession().update(feedback);
    }

    @Override
    @Transactional
    public void saveOrUpdate(Feedback feedback) {
        sessionFactory.getCurrentSession().saveOrUpdate(feedback);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Feedback> findAllTickets() {
        String hql = "SELECT e FROM Feedback e";
        Query<Feedback> query = sessionFactory.getCurrentSession()
                                .createQuery(hql, Feedback.class);
        return query.list();
    }
}