package com.springBootD.framework.constant.factory;

import com.springBootD.application.system.dao.DeptMapper;
import com.springBootD.application.system.dao.MenuMapper;
import com.springBootD.application.system.dao.RoleMapper;
import com.springBootD.application.system.dao.UserMapper;
import com.springBootD.application.system.model.*;
import com.springBootD.framework.constant.enums.ManagerStatus;
import com.springBootD.framework.constant.enums.MenuStatus;
import com.springBootD.framework.utils.ConvertUtils;
import com.springBootD.framework.utils.SpringContextHolder;
import com.springBootD.framework.utils.support.StrKit;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 常量的生产工厂
 *
 */
@Component
@DependsOn("springContextHolder")
public class ConstantFactory implements IConstantFactory {

    private RoleMapper roleMapper = SpringContextHolder.getBean(RoleMapper.class);
    private DeptMapper deptMapper = SpringContextHolder.getBean(DeptMapper.class);
//    private DictMapper dictMapper = SpringContextHolder.getBean(DictMapper.class);
    private UserMapper userMapper = SpringContextHolder.getBean(UserMapper.class);
    private MenuMapper menuMapper = SpringContextHolder.getBean(MenuMapper.class);
//    private NoticeMapper noticeMapper = SpringContextHolder.getBean(NoticeMapper.class);

    public static IConstantFactory me() {
        return SpringContextHolder.getBean("constantFactory");
    }

    /**
     * 根据用户id获取用户名称
     *
     */
    @Override
    public String getUserNameById(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user != null) {
            return user.getName();
        } else {
            return "--";
        }
    }

    /**
     * 根据用户id获取用户账号
     *
     */
    @Override
    public String getUserAccountById(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user != null) {
            return user.getAccount();
        } else {
            return "--";
        }
    }

    /**
     * 通过角色ids获取角色名称
     */
    @Override
    public String getRoleName(String roleIds) {
        Integer[] roles = ConvertUtils.toIntArray(roleIds);
        StringBuilder sb = new StringBuilder();
        for (int role : roles) {
            Role roleObj = roleMapper.selectByPrimaryKey(role);
            if (roleObj != null && StringUtils.isNotEmpty(roleObj.getName())) {
                sb.append(roleObj.getName()).append(",");
            }
        }
        return StrKit.removeSuffix(sb.toString(), ",");
    }

    /**
     * 通过角色id获取角色名称
     */
    @Override
    public String getSingleRoleName(Integer roleId) {
        if (0 == roleId) {
            return "--";
        }
        Role roleObj = roleMapper.selectByPrimaryKey(roleId);
        if (roleObj != null  && StringUtils.isNotEmpty(roleObj.getName())) {
            return roleObj.getName();
        }
        return "";
    }

    /**
     * 通过角色id获取角色英文名称
     */
    @Override
    public String getSingleRoleTip(Integer roleId) {
        if (0 == roleId) {
            return "--";
        }
        Role roleObj = roleMapper.selectByPrimaryKey(roleId);
        if (roleObj != null  && StringUtils.isNotEmpty(roleObj.getName())) {
            return roleObj.getTips();
        }
        return "";
    }

    /**
     * 获取部门名称
     */
    @Override
    public String getDeptName(Integer deptId) {
        Dept dept = deptMapper.selectByPrimaryKey(deptId);
        if (dept != null  && StringUtils.isNotEmpty(dept.getFullname())) {
            return dept.getFullname();
        }
        return "";
    }

    /**
     * 获取菜单的名称们(多个)
     */
    @Override
    public String getMenuNames(String menuIds) {
        Integer[] menus = ConvertUtils.toIntArray(menuIds);
        StringBuilder sb = new StringBuilder();
        for (int menu : menus) {
            Menu menuObj = menuMapper.selectByPrimaryKey(menu);
            if (menuObj != null  && StringUtils.isNotEmpty(menuObj.getName())) {
                sb.append(menuObj.getName()).append(",");
            }
        }
        return StrKit.removeSuffix(sb.toString(), ",");
    }

    /**
     * 获取菜单名称
     */
    @Override
    public String getMenuName(Integer menuId) {
        if (menuId == null) {
            return "";
        } else {
            Menu menu = menuMapper.selectByPrimaryKey(menuId);
            if (menu == null) {
                return "";
            } else {
                return menu.getName();
            }
        }
    }

    /**
     * 获取菜单名称通过编号
     */
    @Override
    public String getMenuNameByCode(String code) {
        if (StringUtils.isEmpty(code)) {
            return "";
        } else {
            Menu param = new Menu();
            param.setCode(code);
            Menu menu = menuMapper.selectOne(param);
            if (menu == null) {
                return "";
            } else {
                return menu.getName();
            }
        }
    }

    /**
     * 获取字典名称
     */
    @Override
    public String getDictName(Integer dictId) {
//        if (dictId != null) {
//            return "";
//        } else {
//            Dict dict = dictMapper.selectByPrimaryKey(dictId);
//            if (dict == null) {
//                return "";
//            } else {
//                return dict.getName();
//            }
//        }
        return null;
    }

    /**
     * 获取通知标题
     */
    @Override
    public String getNoticeTitle(Integer dictId) {
//        if (dictId != null) {
//            return "";
//        } else {
//            Notice notice = noticeMapper.selectByPrimaryKey(dictId);
//            if (notice == null) {
//                return "";
//            } else {
//                return notice.getTitle();
//            }
//        }
        return null;
    }

    /**
     * 根据字典名称和字典中的值获取对应的名称
     */
    @Override
    public String getDictsByName(String name, Integer val) {
//        Dict temp = new Dict();
//        temp.setName(name);
//        Dict dict = dictMapper.selectOne(temp);
//        if (dict == null) {
//            return "";
//        } else {
//            Wrapper<Dict> wrapper = new EntityWrapper<>();
//            wrapper = wrapper.eq("pid", dict.getId());
//            List<Dict> dicts = dictMapper.selectList(wrapper);
//            for (Dict item : dicts) {
//                if (item.getNum() != null && item.getNum().equals(val)) {
//                    return item.getName();
//                }
//            }
//            return "";
//        }
        return null;
    }

    /**
     * 获取性别名称
     */
    @Override
    public String getSexName(Integer sex) {
        return getDictsByName("性别", sex);
    }

    /**
     * 获取用户登录状态
     */
    @Override
    public String getStatusName(Integer status) {
        return ManagerStatus.valueOf(status);
    }

    /**
     * 获取菜单状态
     */
    @Override
    public String getMenuStatusName(Integer status) {
        return MenuStatus.valueOf(status);
    }

    /**
     * 查询字典
     */
    @Override
    public List<Dict> findInDict(Integer id) {
//        if (StringUtils.isEmpty(id)) {
//            return null;
//        } else {
//            EntityWrapper<Dict> wrapper = new EntityWrapper<>();
//            List<Dict> dicts = dictMapper.selectList(wrapper.eq("pid", id));
//            if (dicts == null || dicts.size() == 0) {
//                return null;
//            } else {
//                return dicts;
//            }
//        }
        return null;
    }

}
