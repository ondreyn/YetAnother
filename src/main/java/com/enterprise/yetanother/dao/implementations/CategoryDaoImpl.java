package com.enterprise.yetanother.dao.implementations;

import com.enterprise.yetanother.entities.Category;
import com.enterprise.yetanother.dao.interfaces.CategoryDao;
//import com.enterprise.yetanother.entities.Category;
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
public class CategoryDaoImpl implements CategoryDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public void create(Category category) {
        if (category != null) {
            sessionFactory.getCurrentSession().save(category);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Category findByName(String categoryName) {
        if (categoryName != null) {
            String hql = "SELECT e FROM Category e WHERE e.name = " +
                                                        ":categoryName";

            Query<Category> query = sessionFactory.getCurrentSession()
                                    .createQuery(hql, Category.class);
            query.setParameter("categoryName", categoryName.trim());
            return query.uniqueResult();
        } else {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        String hql = "SELECT e FROM Category e";

        Query<Category> query = sessionFactory.getCurrentSession()
                                .createQuery(hql, Category.class);
        return query.list();
    }

    @Override
    @Transactional
    public void persist(Category category) {
        sessionFactory.getCurrentSession().persist(category);
    }

    @Override
    @Transactional
    public void update(Category category) {
        sessionFactory.getCurrentSession().update(category);
    }

    @Override
    @Transactional
    public void saveOrUpdate(Category category) {
        sessionFactory.getCurrentSession().saveOrUpdate(category);
    }
}
