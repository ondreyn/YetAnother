package com.enterprise.yetanother.init;

import com.enterprise.yetanother.configuration.WebAppConfig;
import com.enterprise.yetanother.configuration.WebSecurityConfig;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;
import java.io.File;

/**
 *@author andrey
 */
@EnableWebMvc
public class WebInitializer extends
             AbstractAnnotationConfigDispatcherServletInitializer {

    private int maxUploadSizeInMb = 5 * 1024 * 1024;

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] {WebAppConfig.class, WebSecurityConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] {};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected Filter[] getServletFilters() {
        return null;
	}

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {

        File uploadDirectory = new File(System.getProperty("java.io.tmpdir"));

        MultipartConfigElement multipartConfigElement =
                new MultipartConfigElement(uploadDirectory.getAbsolutePath(),
                                           maxUploadSizeInMb,
						                   maxUploadSizeInMb * 2,
                                           maxUploadSizeInMb / 2);
        registration.setMultipartConfig(multipartConfigElement);
    }
}