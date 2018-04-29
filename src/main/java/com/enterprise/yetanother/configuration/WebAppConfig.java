package com.enterprise.yetanother.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
//import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *@author andrey
 */
@Configuration
@EnableWebMvc
@EnableTransactionManagement
@PropertySource({"classpath:hibernate.properties"})
@ComponentScan(basePackages = {"com.enterprise.yetanother"})
//@ComponentScan(basePackages = {"com.novikov.enterprise.yetanother"})
public class WebAppConfig implements WebMvcConfigurer {

    final static Logger LOGGER = LoggerFactory.getLogger(WebAppConfig.class);

    @Value("${hibernate.show_sql}")
    private boolean show_sql;

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

    @Value("${hibernate.format_sql}")
    private String formatSql;

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

    @Autowired
    private List<Formatter<?>> formatters;

    /*@Bean
    public static PropertySourcesPlaceholderConfigurer
                                        propertySourcesPlaceholderConfigurer() {
        LOGGER.info("[propertySourcesPlaceholderConfigurer Bean]");
        return new PropertySourcesPlaceholderConfigurer();
    }*/

    @Bean
    public DataSource dataSource() {
        LOGGER.info("[dataSource Bean]");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUsername(this.username);
        dataSource.setPassword(this.password);
        dataSource.setUrl(this.url);
        dataSource.setDriverClassName(this.driverClassName);

        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {

        LOGGER.info("[sessionFactory Bean]");
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
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
        hibernateProperties.put("hibernate.show_sql", show_sql);
        hibernateProperties.put("hibernate.format_sql", formatSql);
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
        HibernateTransactionManager transactionManager
                = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver(){
        LOGGER.info("[SpringResourceTemplateResolver Bean]");
        final SpringResourceTemplateResolver templateResolver =
                                          new SpringResourceTemplateResolver();
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(true);
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine(){
        LOGGER.info("[SpringTemplateEngine Bean]");
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        templateEngine.addDialect("sec", new SpringSecurityDialect());
        return templateEngine;
    }

    @Bean
    public ThymeleafViewResolver viewResolver(){
        LOGGER.info("[ThymeleafViewResolver Bean]");
        final ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        return viewResolver;
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer
                                                              configurer) {
        configurer.mediaType("json", MediaType.APPLICATION_JSON);
        configurer.mediaType("binary", MediaType.APPLICATION_OCTET_STREAM);
    }

    @Bean
    public ByteArrayHttpMessageConverter byteArrayHttpMessageConverter() {
        LOGGER.info("[ByteArrayHttpMessageConverter Bean]");
        ByteArrayHttpMessageConverter arrayHttpMessageConverter =
                                      new ByteArrayHttpMessageConverter();
        arrayHttpMessageConverter.setSupportedMediaTypes(
                                  getSupportedMediaTypes());
        return arrayHttpMessageConverter;
    }

    private List<MediaType> getSupportedMediaTypes() {
        List<MediaType> list = new ArrayList<>();
        list.add(MediaType.IMAGE_JPEG);
        list.add(MediaType.IMAGE_PNG);
        list.add(MediaType.APPLICATION_PDF);
        list.add(MediaType.APPLICATION_OCTET_STREAM);
        list.add(MediaType.parseMediaType("application/msword"));
        list.add(MediaType.parseMediaType(
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
        ));
        return list;
    }

    @Bean
    public MappingJackson2HttpMessageConverter
                                        mappingJackson2HttpMessageConverter() {
        LOGGER.info("[MappingJackson2HttpMessageConverter Bean]");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MappingJackson2HttpMessageConverter converter =
                                    new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);
        return converter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>>
                                           converters) {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder()
                                              .indentOutput(true);
        ByteArrayHttpMessageConverter byteArrayHttpMessageConverter =
                                            new ByteArrayHttpMessageConverter();
        converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
        converters.add(byteArrayHttpMessageConverter);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }

    @Bean
    public MultipartResolver multipartResolver() {
        LOGGER.info("[MultipartResolver Bean]");
        return new StandardServletMultipartResolver();
    }

    @Override
    public void addFormatters(final FormatterRegistry registry) {
        for (Formatter<?> formatter: formatters) {
            registry.addFormatter(formatter);
        }
    }
}