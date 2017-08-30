package com.springBootD.application.system.dao;

import com.springBootD.application.system.model.Relation;
import com.springBootD.framework.mybatis.MyMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by tan on 2017/7/31.
 */
@Mapper
public interface RelationMapper extends MyMapper<Relation> {

}
