package com.springBootD;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class Application extends WebMvcConfigurerAdapter {

    protected final static Logger logger = LoggerFactory.getLogger(Application.class);

//    @Autowired
//    GunsProperties gunsProperties;
//
//    /**
//     * 增加swagger的支持
//     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        if(gunsProperties.getSwaggerOpen()){
//            registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
//            registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//        }
//    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}