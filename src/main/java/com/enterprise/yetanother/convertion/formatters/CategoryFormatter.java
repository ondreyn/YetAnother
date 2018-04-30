package com.enterprise.yetanother.convertion.formatters;

import com.enterprise.yetanother.entities.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 *@author andrey
 */
@Component
public class CategoryFormatter implements Formatter<Category> {

    final static Logger LOGGER = LoggerFactory
                                 .getLogger(CategoryFormatter.class);

    @Override
    public Category parse(String s, Locale locale) {
        LOGGER.info("CategoryFormatter parse s: " + s);
        Category result = new Category();
        result.setName(s);
        return result;
    }

    @Override
    public String print(Category category, Locale locale) {
        return category != null ? category.getName().toString() : "";        
    }
}
