package com.springBootD.application.demo.service;

import com.springBootD.application.demo.model.UserInfo;

import java.util.List;

/**
 * Created by tan on 2017/7/31.
 */
public interface IUserInfoService {

    List<UserInfo> getAll(UserInfo UserInfo);

    UserInfo getById(Integer id);

    void deleteById(Integer id);

    void save(UserInfo country);

}
