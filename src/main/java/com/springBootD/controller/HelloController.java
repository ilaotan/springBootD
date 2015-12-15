package com.springBootD.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springBootD.config.ConnectionConfig;

/**
 * Created by wenchao.ren on 2014/4/26.
 */
@RestController
public class HelloController {
 
	@Value("${name}")
	private String testName;
	@Value("${random.int}")
	private Integer testInt;
	@Autowired
	private ConnectionConfig cc;
	
	 @RequestMapping(value ="/", method = RequestMethod.GET)
	    public String index(){
	        return "this is index";
	    }
	
    @RequestMapping(value ="/hello", method = RequestMethod.GET)
    public String hello(){
    	System.out.println(cc.getUsername());
        return "hello world4"+testName+testInt;
    }
}