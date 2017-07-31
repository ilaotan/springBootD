package com.springBootD.application.demo.dao;


import com.springBootD.application.demo.model.City;
import com.springBootD.framework.mybatis.MyMapper;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface CityMapper extends MyMapper<City> {
}
