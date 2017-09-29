package com.springBootD;

import com.springBootD.framework.config.properties.SpringBootDProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableAsync
public class Application extends WebMvcConfigurerAdapter {

    protected final static Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    SpringBootDProperties springBootDProperties;

    /**
     * 增加swagger的支持
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if(springBootDProperties.getSwaggerOpen()){
            registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
            registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}