package com.springBootD.application.demo.dao;


import com.springBootD.application.demo.model.Country;
import com.springBootD.framework.mybatis.MyMapper;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface CountryMapper extends MyMapper<Country> {

}