//package com.springBootD;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
//import com.springBootD.framework.config.properties.SpringBootDProperties;
//
//@SpringBootApplication
//@EnableAsync
//public class ApplicationNoWeb {
//
//
//    public static void main(String[] args) {
//        new SpringApplicationBuilder(ApplicationNoWeb.class)
//                .web(false)
//                .run(args);
//    }
//}