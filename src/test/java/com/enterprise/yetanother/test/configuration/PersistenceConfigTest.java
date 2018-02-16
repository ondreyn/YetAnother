package com.enterprise.yetanother.test.configuration;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource({"classpath:test.properties"})
@ComponentScan(basePackages = {
        "com.enterprise.yetanother.dao",
        "com.enterprise.yetanother.enums",
        "com.enterprise.yetanother.utilities",
})
public class PersistenceConfigTest {

    final static Logger LOGGER = LoggerFactory
                                 .getLogger(PersistenceConfigTest.class);

    @Value("${hibernate.current_session_context_class}")
    private String context;

    @Value("${hibernate.dialect}")
    private String dialect;

    @Value("${hibernate.connection.release_mode}")
    private String release_mode;

    @Value("${hibernate.connection.username}")
    private String username;

    @Value("${hibernate.connection.password}")
    private String password;

    @Value("${hibernate.connection.driver_class}")
    private String driverClassName;

    @Value("${hibernate.connection.url}")
    private String url;

    @Value("${hibernate.hbm2ddl.auto}")
    private String autoDDL;

    @Value("${hibernate.connection.autoReconnect}")
    private boolean autoReconnect;

    @Value("${hibernate.connection.autocommit}")
    private boolean autocommit;

    @Value("${hibernate.hbm2ddl.import_files}")
    private String initSQL;

    @Value("${connection.pool_size}")
    private int poolSize;

    @Value("${hibernate.jdbc.use_streams_for_binary}")
    private boolean streamsForBinary;

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LOGGER.info("[sessionFactory Bean]");

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUsername(this.username);
        dataSource.setPassword(this.password);
        dataSource.setUrl(this.url);
        dataSource.setDriverClassName(this.driverClassName);

        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan(
                "com.novikov.enterprise.yetanother.entities"
        );
        sessionFactory.setHibernateProperties(hibernateProperties());

        BootstrapServiceRegistryBuilder bootstrapServiceRegistryBuilder =
                                        new BootstrapServiceRegistryBuilder();

        ServiceRegistry serviceRegistry = bootstrapServiceRegistryBuilder.build();
        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        sessionFactory.setMetadataSources(metadataSources);
        metadataSources.buildMetadata();

        return sessionFactory;
    }

    @Bean
    public Properties hibernateProperties() {
        LOGGER.info("[hibernateProperties Bean]");

        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", dialect);
        hibernateProperties.put(
                "hibernate.current_session_context_class", context
        );
        hibernateProperties.put("hibernate.hbm2ddl.auto", autoDDL);
        hibernateProperties.put(
                "hibernate.connection.release_mode", release_mode
        );
        hibernateProperties.put(
                "hibernate.connection.autoReconnect", autoReconnect
        );
        hibernateProperties.put(
                "hibernate.connection.autocommit", autocommit
        );
        hibernateProperties.put("hibernate.hbm2ddl.import_files", initSQL);
        hibernateProperties.put("connection.pool_size", poolSize);
        hibernateProperties.put(
                "hibernate.jdbc.use_streams_for_binary", streamsForBinary);
        return hibernateProperties;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        LOGGER.info("[transactionManager Bean]");
        HibernateTransactionManager transactionManager =
                                    new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }
}
