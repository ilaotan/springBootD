/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.springBootD.application.system.service;

import java.util.List;

import com.springBootD.application.system.entity.AddressEntity;
import com.springBootD.application.system.entity.UserEntity;
import com.springBootD.application.system.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService{
	@Autowired
	private UserDao userRepository;
	
	/**
	 * 查询所有的User对象
	 * @return
	 */
	public List<UserEntity> findAll(){
		return userRepository.findAll();
	}
	
	/**
	 * save user
	 * @param user
	 */
	public void save(UserEntity user) {
		for(AddressEntity address: user.getAddresses()) {
			address.setUser(user);
		}
		userRepository.save(user);
	}
}
