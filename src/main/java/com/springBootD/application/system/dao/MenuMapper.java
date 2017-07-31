package com.springBootD.application.system.dao;

import com.springBootD.application.system.model.Menu;
import com.springBootD.application.system.model.node.MenuNode;
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
public interface MenuMapper extends MyMapper<Menu> {

    /**
     * 根据条件查询菜单
     *
     */
    List<Map<String, Object>> selectMenus(@Param("condition") String condition, @Param("level") String level);

    /**
     * 根据条件查询菜单
     *
     */
    List<Integer> getMenuIdsByRoleId(@Param("roleId") Integer roleId);

    /**
     * 获取菜单列表树
     *
     */
    List<ZTreeNode> menuTreeList();

    /**
     * 获取菜单列表树
     *
     */
    List<ZTreeNode> menuTreeListByMenuIds(List<Integer> menuIds);

    /**
     * 删除menu关联的relation
     *
     */
    int deleteRelationByMenu(Integer menuId);

    /**
     * 获取资源url通过角色id
     *
     */
    List<String> getResUrlsByRoleId(Integer roleId);

    /**
     * 根据角色获取菜单
     *
     */
    List<MenuNode> getMenusByRoleIds(List<Integer> roleIds);

}
