package com.enterprise.yetanother.dao.implementations;

import com.enterprise.yetanother.entities.Attachment;
import com.enterprise.yetanother.dao.interfaces.AttachmentDao;
import com.enterprise.yetanother.entities.Attachment;
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
public class AttachmentDaoImpl implements AttachmentDao {

    final static Logger LOGGER = LoggerFactory
                                 .getLogger(AttachmentDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public void create(Attachment attachment) {
        if (attachment != null) {
            sessionFactory.getCurrentSession().save(attachment);
        }
    }

    @Override
    @Transactional
    public void createFromList(List<Attachment> attachments) {
        try {
            if (attachments != null && !attachments.isEmpty()) {
                for (Attachment attachment : attachments) {
                    create(attachment);
                }
            }
        } catch (Exception e) {
            LOGGER.error("[createFromList: Exception thrown!]", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Attachment> findByTicket(Long ticketId) {
        if (ticketId != null) {
            String hql = "SELECT e FROM Attachment e WHERE e.ticket.id = " +
                                                           ":ticketId";

            Query<Attachment> query = sessionFactory.getCurrentSession()
                                      .createQuery(hql, Attachment.class);
            query.setParameter("ticketId", ticketId);
            return query.list();
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public void persist(Attachment attachment) {
        if (attachment != null) {
            sessionFactory.getCurrentSession().persist(attachment);
        }
    }

    @Override
    @Transactional
    public void update(Attachment attachment) {
        sessionFactory.getCurrentSession().update(attachment);
    }

    @Override
    @Transactional
    public void saveOrUpdate(Attachment attachment) {
        sessionFactory.getCurrentSession().saveOrUpdate(attachment);
    }

    @Override
    @Transactional
    public void save(Attachment attachment) {
        create(attachment);
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional
    public void delete(Long attachmentId) {
        if (attachmentId != null) {
            String hql = "DELETE FROM Attachment e WHERE e.id = :attachmentId";

            Query query = sessionFactory.getCurrentSession().createQuery(hql);
            query.setParameter("attachmentId", attachmentId);
            query.executeUpdate();
            LOGGER.info("[delete: " + attachmentId + " deleted]");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Attachment findById(Long id) {
        if (id != null) {
            String hql = "SELECT e FROM Attachment e WHERE e.id = :id";

            Query<Attachment> query = sessionFactory.getCurrentSession()
                                      .createQuery(hql, Attachment.class);
            query.setParameter("id", id);
            return query.uniqueResult();
        } else {
            LOGGER.warn("[findById: found nothing]");
            return null;
        }
    }
}
