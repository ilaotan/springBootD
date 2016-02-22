/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.springBootD.application.system.service;

import com.springBootD.application.system.entity.UserEntity;
import com.springBootD.application.system.dao.UserDao;
import com.springBootD.framework.utils.EncryptUtils;
import com.springBootD.framework.utils.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 登录信息
 * @author lance
 */
@Service
public class LoginService{
	@Autowired
	private UserDao userRepository;
	/**
	 * 用户登录
	 * @author lance
	 * 2014-6-11下午11:26:05
	 * @param user
	 * @return
	 */
	public UserEntity login(UserEntity user) {
		if(StringUtils.isBlank(user.getEmail())) {
			throw new ServiceException("用户名不能为空");
		}
		
		if(StringUtils.isBlank(user.getPassword())) {
			throw new ServiceException("密码不能为空");
		}
		
		UserEntity userEntity = userRepository.findByEmail(user.getEmail());
		if(null == userEntity){
			throw new ServiceException("用户名不存在");
		}
		
		String password = EncryptUtils.encryptMD5(user.getPassword());
		if(!StringUtils.equals(password, userEntity.getPassword())){
			throw new ServiceException("密码输入错误");
		}
		
		return userEntity;
	}

}
