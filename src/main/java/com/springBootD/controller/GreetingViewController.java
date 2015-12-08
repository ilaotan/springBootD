package com.springBootD.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingViewController {
	
	@RequestMapping(path="/greetingView",method=RequestMethod.GET)
	public String greetingView (@RequestParam(name="name",defaultValue="张三11")String name,Model model){
		
		model.addAttribute("name", name);
		return "greetingView";
	}
}
