package com.enterprise.yetanother.configuration;

import com.enterprise.yetanother.enums.Roles;
import com.enterprise.yetanother.services.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

import java.util.List;

/**
 *@author andrey
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    final static Logger LOGGER = LoggerFactory
                                 .getLogger(WebSecurityConfig.class);

    @Autowired
    private UserService userService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
                                                            throws Exception {

        LOGGER.info("[configureGlobal GO]");

        List<com.enterprise.yetanother.entities.User> users =
                                               userService.getAllUsers();

        if (!users.isEmpty()) {
            for (com.enterprise.yetanother.entities.User user: users) {
                auth
                        .inMemoryAuthentication()
                        .withUser(User.withDefaultPasswordEncoder()
                                .username(user.getEmail())
                                .password(user.getUserPassword())
                                .roles(user.getRole().toString())
                                .build());
            }
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/", "/favicon.ico","/resources/**")
            .permitAll()
            .and()
            .formLogin()
            .loginPage("/")
            .successForwardUrl("/postLogin")
            .permitAll()
            .and()
            .authorizeRequests()
            .antMatchers("/ticketsPage", "/ticketOverviewPage/*")
            .hasAnyRole(Roles.EMPLOYEE.toString().trim(),
                        Roles.MANAGER.toString().trim(),
                        Roles.ENGINEER.toString().trim())
            .and()
            .authorizeRequests()
            .antMatchers("/ticketCreatePage", "/ticketEditPage/*",
                         "/ticketFeedbackPage/*/*")
            .hasAnyRole(Roles.EMPLOYEE.toString().trim(),
                        Roles.MANAGER.toString().trim())
            .and()
            .logout()
            .logoutSuccessUrl("/");
    }
}
