package com.enterprise.yetanother.dao.interfaces;

import com.enterprise.yetanother.entities.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *@author andrey
 */
@Repository
public interface CategoryDao {

    void create(Category category);
    Category findByName(String categoryName);
    List<Category> getAllCategories();
    void persist(Category category);
    void update(Category category);
    void saveOrUpdate(Category category);
}
