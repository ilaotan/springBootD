/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.springBootD.application.system.dao;

import java.util.List;

import com.springBootD.application.system.entity.NavigationEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NavigationDao extends JpaRepository<NavigationEntity, Long>{
	
	/**
	 * 根据type查询菜单
	 * @param type
	 * @return
	 */
	List<NavigationEntity> findByTypeAndStatusIs(int type, int status);
}
