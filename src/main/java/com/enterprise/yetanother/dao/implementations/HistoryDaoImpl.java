package com.enterprise.yetanother.dao.implementations;

import com.enterprise.yetanother.entities.History;
import com.enterprise.yetanother.dao.interfaces.HistoryDao;
//import com.enterprise.yetanother.entities.History;
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
public class HistoryDaoImpl implements HistoryDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public void create(History history) {
        if (history != null) {
            sessionFactory.getCurrentSession().save(history);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<History> findByTicket(Long ticketId, Integer quantity) {
        if (ticketId != null && quantity != null) {
            String hql = "SELECT e FROM History e WHERE e.ticket.id = " +
                         ":ticketId ORDER BY e.id DESC";
            Query<History> query = sessionFactory.getCurrentSession()
                                   .createQuery(hql, History.class);
            query.setParameter("ticketId", ticketId);
            if (quantity == 0) {
                return query.list();
            } else {
                query.setMaxResults(quantity);
                return query.list();
            }
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public void persist(History history) {
        sessionFactory.getCurrentSession().persist(history);
    }

    @Override
    @Transactional
    public void update(History history) {
        sessionFactory.getCurrentSession().update(history);
    }

    @Override
    @Transactional
    public void saveOrUpdate(History history) {
        sessionFactory.getCurrentSession().saveOrUpdate(history);
    }
}