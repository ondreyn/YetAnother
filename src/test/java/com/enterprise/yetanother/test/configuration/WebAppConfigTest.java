package com.enterprise.yetanother.test.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.enterprise.yetanother.controllers.InitController;
import com.enterprise.yetanother.controllers.tickets.TicketsController;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {
        "com.enterprise.yetanother.test.configuration",
        "com.enterprise.yetanother.convertion",
        "com.enterprise.yetanother.services",
        "com.enterprise.yetanother.test.services",
        "com.enterprise.yetanother.utilities",
        "com.enterprise.yetanother.init",
        "com.enterprise.yetanother.test.controllers"
})
public class WebAppConfigTest implements WebMvcConfigurer {

    final static Logger LOGGER = LoggerFactory.getLogger(WebAppConfigTest.class);

    @Autowired
    private List<Formatter<?>> formatters;

    @Bean
    public static PropertySourcesPlaceholderConfigurer
    propertySourcesPlaceholderConfigurer() {
        LOGGER.info("[propertySourcesPlaceholderConfigurer Bean]");
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver =
                                     new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/templates/");
        viewResolver.setSuffix(".html");

        return viewResolver;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        registry.addViewController("/ticketsPage")
                .setViewName("AllTickets");
        registry.addViewController("/").setViewName("Login");
        registry.addViewController("/ticketCreatePage")
                .setViewName("CreateTicket");
        registry.addViewController("/ticketOverviewPage/*")
                .setViewName("TicketOverview");
        registry.addViewController("/ticketEditPage/*")
                .setViewName("EditTicket");
        registry.addViewController("/ticketFeedbackPage/*/*")
                .setViewName("FeedbackPage");
    }

    @Override
    public void configureDefaultServletHandling(
                                DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void configureContentNegotiation(
                                ContentNegotiationConfigurer configurer) {
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
            "application/vnd.openxmlformats-officedocument" +
            ".wordprocessingml.document"
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

    @Bean
    public InitController initController() {
        return Mockito.mock(InitController.class);
    }

    @Bean(name = "TicketsControllerMock")
    public TicketsController ticketsController() {
        return Mockito.mock(TicketsController.class);
    }
}
