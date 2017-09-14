package com.springBootD.application.system.model;

import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


/**
 * 角色表
 */

@Getter
@Setter
@Table(name = "system_role")
public class Role extends BaseEntity{

    private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
	private Integer num;
    /**
     * 父角色id
     */
	private Integer pid;
    /**
     * 角色名称
     */
	private String name;
    /**
     * 部门名称
     */
	private Integer deptid;
    /**
     * 提示
     */
	private String tips;
    /**
     * 保留字段(暂时没用）
     */
	private Integer version;

}
