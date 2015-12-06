package com.springBootD;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * run这个主方法来运行框架
 * @author tan
 *
 */
//@Configuration  
//@ComponentScan  
//@EnableAutoConfiguration
////@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication //等同于 @Configuration @EnableAutoConfiguration @ComponentScan
public class Application {
	
	public static void main(String[] args) {
		
		SpringApplication app = new SpringApplication(Application.class);
	    app.run(args);
	
	}
	
}
