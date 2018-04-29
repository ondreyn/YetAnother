package com.enterprise.yetanother.dao.implementations;

import com.enterprise.yetanother.entities.Comment;
import com.enterprise.yetanother.dao.interfaces.CommentDao;
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
public class CommentDaoImpl implements CommentDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public void create(Comment comment) {
        if (comment != null) {
            sessionFactory.getCurrentSession().save(comment);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findByTicket(Long ticketId, Integer quantity) {
        if (ticketId != null && quantity != null) {
            String hql = "SELECT e FROM Comment e WHERE e.ticket.id = " +
                         ":ticketId ORDER BY e.id DESC";
            Query<Comment> query = sessionFactory.getCurrentSession()
                                   .createQuery(hql, Comment.class);
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
    public void persist(Comment comment) {
        sessionFactory.getCurrentSession().persist(comment);
    }

    @Override
    @Transactional
    public void update(Comment comment) {
        sessionFactory.getCurrentSession().update(comment);
    }

    @Override
    @Transactional
    public void saveOrUpdate(Comment comment) {
        sessionFactory.getCurrentSession().saveOrUpdate(comment);
    }
}
