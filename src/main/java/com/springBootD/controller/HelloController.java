package com.springBootD.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wenchao.ren on 2014/4/26.
 */
@RestController
public class HelloController {
 
	 @RequestMapping(value ="/", method = RequestMethod.GET)
	    public String index(){
	        return "this is index";
	    }
	
    @RequestMapping(value ="/hello", method = RequestMethod.GET)
    public String hello(){
        return "hello world";
    }
}