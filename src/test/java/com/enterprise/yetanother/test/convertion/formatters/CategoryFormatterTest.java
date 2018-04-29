package com.enterprise.yetanother.test.convertion.formatters;

import com.enterprise.yetanother.dao.interfaces.CategoryDao;
import com.enterprise.yetanother.entities.Category;
import com.enterprise.yetanother.test.configuration.PersistenceConfigTest;
import com.enterprise.yetanother.test.configuration.WebAppConfigTest;
import com.enterprise.yetanother.convertion.formatters.CategoryFormatter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Locale;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {
        WebAppConfigTest.class,
        PersistenceConfigTest.class
})
public class CategoryFormatterTest {

    final static Logger LOGGER = LoggerFactory
                                 .getLogger(CategoryFormatterTest.class);
    @Autowired
    private CategoryFormatter categoryFormatter;

    @Autowired
    private CategoryDao categoryDao;

    @Test
    public void parseTest() {
        LOGGER.info("[parseTest GO]");

        String str = "APPLICATION_SERVICES";
        Category category = categoryFormatter.parse(str, Locale.getDefault());

        Assert.assertEquals("APPLICATION_SERVICES", category.getName());
    }

    @Test(expected = AssertionError.class)
    public void parseFailedTest() {
        LOGGER.info("[parseFailedTest GO]");

        String str = "APPLICATION_SERVICES";
        Category category = categoryFormatter.parse(str, Locale.getDefault());

        Assert.assertEquals("BENEFITS_PAPERWORK", category.getName());
    }

    @Test
    public void printTest() {
        LOGGER.info("[printTest GO]");

        String str = "APPLICATION_SERVICES";
        Category category = categoryDao.findByName(str);

        String printed = categoryFormatter.print(category, Locale.getDefault());

        Assert.assertEquals(str, printed);
    }

    @Test(expected = AssertionError.class)
    public void printFailedTest() {
        LOGGER.info("[printFailedTest GO]");

        String str = "APPLICATION_SERVICES";
        Category category = categoryDao.findByName(str);

        String printed = categoryFormatter.print(category, Locale.getDefault());

        Assert.assertEquals("SECURITY_ACCESS", printed);
    }
}
