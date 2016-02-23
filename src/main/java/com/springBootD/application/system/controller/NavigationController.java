
package com.springBootD.application.system.controller;

import java.util.List;

import com.springBootD.application.system.entity.NavigationEntity;
import com.springBootD.application.system.service.NavigationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/user/nav/")
public class NavigationController {
	@Autowired
	private NavigationService navigationService;
	
	/**
	 * 查询Home页左边导航
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="homeleft",produces="application/json;charset=utf-8")
	public String homeLeft(){
		List<NavigationEntity> navs = navigationService.findByType(NavigationEntity.TYPE_HOME_LEFT);
		
		return JSON.toJSONString(navs);
	}
}
