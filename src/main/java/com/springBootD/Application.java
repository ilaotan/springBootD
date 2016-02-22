package com.springBootD;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@EnableScheduling
@EnableAutoConfiguration
@ComponentScan("com.springBootD")
@EntityScan("com.springBootD.application.system.entity")
@EnableJpaRepositories("com.springBootD.application.system.dao")
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
    }

    //public static void main(String[] args) throws Exception {
    //    SpringApplication app = new SpringApplication(Application.class);
    //    //启动时的提示文字
    //    app.setBannerMode(Banner.Mode.OFF);
    //    app.run(args);
    //}

}