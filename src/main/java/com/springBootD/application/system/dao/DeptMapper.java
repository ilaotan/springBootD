package com.springBootD.application.system.dao;

import com.springBootD.application.system.model.Dept;
import com.springBootD.application.system.model.node.ZTreeNode;
import com.springBootD.framework.mybatis.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by tan on 2017/7/31.
 */
@Mapper
public interface DeptMapper extends MyMapper<Dept> {

    /**
     * 获取ztree的节点列表
     */
    List<ZTreeNode> tree();

    List<Map<String, Object>> list(@Param("condition") String condition);

}
