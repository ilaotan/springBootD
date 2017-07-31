package com.springBootD.application.demo.dao;


import com.springBootD.application.demo.model.UserInfo;
import com.springBootD.framework.mybatis.MyMapper;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserInfoMapper extends MyMapper<UserInfo> {
}
