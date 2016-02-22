/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.springBootD.application.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/home/")
public class HomeController {
	
	/**
	 * 处理图片生成
	 * @return
	 */
	@RequestMapping("holder")
	public String holder(){
		return "user/customer/img-hoder.jsp";
	}
	
	/**
	 * 处理模板方法
	 * @return
	 */
	@RequestMapping("tmpl")
	public String tmpl(){
		return "user/customer/template.jsp";
	}
	
	@RequestMapping("adduser")
	public String addUser(){
		return "user/customer/user-add.jsp";
	}
}
