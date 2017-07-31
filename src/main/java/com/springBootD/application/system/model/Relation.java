package com.springBootD.application.system.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;

/**
 * 角色和菜单关联表
 */

@Getter
@Setter
@Table(name = "system_menu_role_relation")
public class Relation extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单id
     */
	private Integer menuid;
    /**
     * 角色id
     */
	private Integer roleid;

}
