/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.springBootD.application.system.service;

import java.util.List;

import com.springBootD.application.system.entity.BaseEntity;
import com.springBootD.application.system.entity.NavigationEntity;
import com.springBootD.application.system.dao.NavigationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class NavigationService{
	@Autowired
	private NavigationDao navigationRepository;
	
	/**
	 * 根据type查询菜单
	 * @param type
	 * @return
	 */
	public List<NavigationEntity> findByType(int type) {
		return navigationRepository.findByTypeAndStatusIs(type, BaseEntity.STATUS_VALID);
	}
}
