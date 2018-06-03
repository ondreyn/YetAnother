package com.enterprise.yetanother.dao.implementations;

import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.entities.User;
import com.enterprise.yetanother.enums.Roles;
import com.enterprise.yetanother.dao.interfaces.UserDao;
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
public class UserDaoImpl implements UserDao {

    final static Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public void create(User user) {
        if (user != null) {
            sessionFactory.getCurrentSession().save(user);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        try {
            String hql = "SELECT e FROM com.enterprise.yetanother" +
                                                             ".entities.User e";
            Query<User> query = sessionFactory.getCurrentSession()
                                .createQuery(hql, User.class);
            List<User> result = query.list();

            return result;
        } catch (IllegalArgumentException ex) {
            LOGGER.error("[getAllUsers: IllegalArgumentException!]", ex);
            return null;
        }
    }

    @Override    
    @Transactional(readOnly = true)
    public User findById(Long id) {
        if (id != null) {
            try {
                String hql = "SELECT e FROM com.enterprise.yetanother" +
                             ".entities.User e WHERE e.id = :id";
                Query<User> query = sessionFactory.getCurrentSession()
                                    .createQuery(hql, User.class);
                query.setParameter("id", id);
                User result = query.uniqueResult();

                if (result != null) {
                    LOGGER.info(String.format("[findById: found user with id:" +
                                " %d]", result.getId()));
                } else {
                    LOGGER.warn("[findById: nothing found]");
                }

                return result;
            } catch (IllegalArgumentException ex) {
                LOGGER.error("[findById: IllegalArgumentException!]", ex);
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        if (email != null) {
            try {
                String hql = "SELECT e FROM com.enterprise.yetanother" +
                             ".entities.User e WHERE e.email = :email";
                Query<User> query = sessionFactory.getCurrentSession()
                                    .createQuery(hql, User.class);
                query.setParameter("email", email);
                User result = query.uniqueResult();

                if (result != null) {
                    LOGGER.info(String.format("[findByEmail: found user " +
                                "email: %s]", result.getEmail()));
                } else {
                    LOGGER.warn("[findByEmail: nothing found]");
                }

                return result;
            } catch (IllegalArgumentException ex) {
                LOGGER.error("[findByEmail: IllegalArgumentException]", ex);
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public void persist(User user) {
        sessionFactory.getCurrentSession().persist(user);
    }

    @Override
    @Transactional
    public void update(User user) {
        sessionFactory.getCurrentSession().update(user);
    }

    @Override
    @Transactional
    public void saveOrUpdate(User user) {
        sessionFactory.getCurrentSession().saveOrUpdate(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllByRole(Roles role) {
        try {
            String hql = "SELECT e FROM com.enterprise.yetanother" +
                         ".entities.User e WHERE e.role = :role";
            Query<User> query = sessionFactory.getCurrentSession()
                                .createQuery(hql, User.class);
            query.setParameter("role", role);
            List<User> result = query.list();

            if (result != null) {
                LOGGER.info(String.format("[getAllByRole: %d users]",
                            result.size()));
            } else {
                LOGGER.warn("[getAllByRole: nothing found]");
            }

            return result;
        } catch (IllegalArgumentException ex) {
            LOGGER.error("[getAllByRole: IllegalArgumentException]", ex);
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User getCreator(Ticket ticket) {

        User owner = ticket.getOwner();
        try {
            String hql = "SELECT e FROM com.enterprise.yetanother" +
                         ".entities.User e WHERE e.id = :ownerId";
            Query<User> query = sessionFactory.getCurrentSession()
                                              .createQuery(hql, User.class);
            query.setParameter("ownerId", owner.getId());
            User result = query.uniqueResult();

            if (result != null) {
                LOGGER.info(String.format("[getCreator: user id: %d]",
                            result.getId()));
            } else {
                LOGGER.warn("[getCreator: nothing found]");
            }
            return result;
        } catch (IllegalArgumentException ex) {
            LOGGER.error("[getCreator: IllegalArgumentException]", ex);
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User getApprover(Ticket ticket) {

        User approver = ticket.getApprover();
        try {
           String hql = "SELECT e FROM com.enterprise.yetanother" +
                        ".entities.User e WHERE e.id = :approverId";
            Query<User> query = sessionFactory.getCurrentSession()
                                .createQuery(hql, User.class);
            query.setParameter("approverId", approver.getId());
            User result = query.uniqueResult();

            if (result != null) {
                LOGGER.info(String.format("[getApprover: user id: %d]",
                            result.getId()));
            } else {
                LOGGER.warn("[getApprover: nothing found]");
            }
            return result;
        } catch (IllegalArgumentException ex) {
            LOGGER.error("[getApprover: IllegalArgumentException]", ex);
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User getAssignee(Ticket ticket) {
        User assignee = ticket.getAssignee();
        try {
            String hql = "SELECT e FROM com.enterprise.yetanother" +
                         ".entities.User e WHERE e.id = :assigneeId";
            Query<User> query = sessionFactory.getCurrentSession()
                                .createQuery(hql, User.class);
            query.setParameter("assigneeId", assignee.getId());
            User result = query.uniqueResult();

            if (result != null) {
                LOGGER.info(String.format("[getAssignee: user id: %d]",
                            result.getId()));
            } else {
                LOGGER.warn("[getAssignee: nothing found]");
            }

            return result;
        } catch (IllegalArgumentException ex) {
            LOGGER.error("[getAssignee: IllegalArgumentException]", ex);
            return null;
        }
    }
}