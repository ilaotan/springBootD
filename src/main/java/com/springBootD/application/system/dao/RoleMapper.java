package com.springBootD.application.system.dao;

import com.springBootD.application.system.model.Role;
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
public interface RoleMapper extends MyMapper<Role> {

    /**
     * 根据条件查询角色列表
     *
     */
    List<Map<String, Object>> selectRoles(@Param("condition") String condition);

    /**
     * 删除某个角色的所有权限
     *
     */
    int deleteRolesById(@Param("roleId") Integer roleId);

    /**
     * 获取角色列表树
     *
     */
    List<ZTreeNode> roleTreeList();

    /**
     * 获取角色列表树
     *
     */
    List<ZTreeNode> roleTreeListByRoleId(String[] roleId);

}
