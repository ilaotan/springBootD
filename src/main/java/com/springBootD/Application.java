package com.springBootD;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.persistence.Basic;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    @Bean
    public LocalValidatorFactoryBean validator(){
        return new LocalValidatorFactoryBean();
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
//        SpringApplication app = new SpringApplication(Application.class);
//        //启动时的提示文字
//        app.setBannerMode(Banner.Mode.OFF);
//        app.run(args);
    }

}