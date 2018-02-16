package com.enterprise.yetanother.services.implementations;

import com.enterprise.yetanother.dao.interfaces.CategoryDao;
import com.enterprise.yetanother.entities.Category;
import com.enterprise.yetanother.services.interfaces.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *@author andrey
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    final static Logger LOGGER = LoggerFactory
                                 .getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<Category> getAllCategories() {
        return categoryDao.getAllCategories();
    }

    @Override
    public List<String> getAllCategoriesNames() {

        List<Category> categories = categoryDao.getAllCategories();
        List<String> categoriesNames = new ArrayList<>();

        try {
            for (Category category : categories) {
                categoriesNames.add(category.getName());
            }
            return categoriesNames;
        } catch (NullPointerException e) {
            LOGGER.error("[getAllCategoriesNames: NullPointerException]", e);
            return null;
        }
    }

    @Override
    public Category getCategory(String categoryName) {
        if (categoryName != null) {
            return categoryDao.findByName(categoryName);
        } else {
            LOGGER.warn("[getCategory: categoryName is null!]");
            return null;
        }
    }
}