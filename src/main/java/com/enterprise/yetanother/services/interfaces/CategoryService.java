package com.enterprise.yetanother.services.interfaces;

import com.enterprise.yetanother.entities.Category;

import java.util.List;

/**
 *@author andrey
 */
public interface CategoryService {

    List<Category> getAllCategories();
    List<String> getAllCategoriesNames();
    Category getCategory(String categoryName);
}
