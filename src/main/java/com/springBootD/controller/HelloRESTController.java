package com.springBootD.controller;

import com.springBootD.config.ConnectionConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *  使用RestController  不能返回视图
 */
@RestController
public class HelloRESTController {

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