package com.springBootD.application.demo.dao;

import com.springBootD.application.demo.model.Country;
import com.springBootD.application.demo.model.Test;
import com.springBootD.framework.mybatis.MyMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author tan20170813
 */
@Mapper
public interface TestMapper extends MyMapper<Test> {
}
