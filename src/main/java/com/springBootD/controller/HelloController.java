package com.springBootD.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


/**
 *
 */
@Controller
public class HelloController {

	@Value("${application.message:Hello World}")
	private String message = "Hello World";


	@RequestMapping(value = "/helloJsp")
	public ModelAndView helloJsp() {
		return new ModelAndView("welcome", "message","12345");
	}
}