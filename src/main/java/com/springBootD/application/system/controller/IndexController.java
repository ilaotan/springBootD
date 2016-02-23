
package com.springBootD.application.system.controller;


import javax.servlet.http.HttpSession;

import com.springBootD.application.system.entity.UserEntity;
import com.springBootD.application.system.service.LoginService;
import com.springBootD.framework.utils.CurrentUserUtils;
import com.springBootD.framework.utils.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * 
 */
@Controller
public class IndexController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private HttpSession session;
	@Autowired
	private LoginService loginService;
	
	/**
	 * 跳转登录页面
	 * @return
	 */
	@RequestMapping(value={"login","/"},method=RequestMethod.GET)
	public String login(){
		return "login.jsp";
	}
	
	/**
	 * 登录成功后跳转页面
	 * @return
	 */
	@RequestMapping(value="login",method=RequestMethod.POST)
	public String login(UserEntity user, RedirectAttributes redirect){
		try {
			user = loginService.login(user);
		} catch (ServiceException e) {
			logger.debug(e.getMessage());
			redirect.addFlashAttribute("err_code", e.getMessage());
			redirect.addFlashAttribute("user", user);
			return "redirect:/login";
		}
		
		CurrentUserUtils.getInstance().serUser(user);
		return "redirect:user/home";
	}
	
	/**
	 * 测试拦截器
	 * @return
	 */
	@RequestMapping("user/home")
	public String home(){
		return "user/home.jsp";
	}
}
