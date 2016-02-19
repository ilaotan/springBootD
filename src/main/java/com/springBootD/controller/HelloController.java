package com.springBootD.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 *
 */
@Controller
public class HelloController {

	@Value("${application.message:Hello World}")
	private String message = "Hello World";


	@RequestMapping(value = "/helloJsp")
	//@ResponseBody  //加上这个后 返回的是字符串"welcome"而不是视图
	public String helloJsp(Model model) {
		model.addAttribute("message","12345");
		return "welcome";
	}
}