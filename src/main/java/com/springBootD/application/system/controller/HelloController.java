package com.springBootD.application.system.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 *
 */
@Controller
public class HelloController {

	@Value("${application.message:Hello World}")
	private String message = "Hello World";


	@RequestMapping(value = "/helloJsp")
	public ModelAndView helloJsp(Model model) {

		return new ModelAndView("welcome", "message","12345");
	}
}