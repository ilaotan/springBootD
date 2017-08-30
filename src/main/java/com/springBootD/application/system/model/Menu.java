package com.springBootD.application.system.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;

/**
 * 菜单表
 */

@Getter
@Setter
@Table(name = "system_menu")
public class Menu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单编号
     */
	private String code;
    /**
     * 菜单父编号
     */
	private String pcode;
    /**
     * 当前菜单的所有父菜单编号
     */
	private String pcodes;
    /**
     * 菜单名称
     */
	private String name;
    /**
     * 菜单图标
     */
	private String icon;
    /**
     * url地址
     */
	private String url;
    /**
     * 菜单排序号
     */
	private Integer num;
    /**
     * 菜单层级
     */
	private Integer levels;
    /**
     * 是否是菜单（1：是  0：不是）
     */
	private Integer ismenu;
    /**
     * 备注
     */
	private String tips;
    /**
     * 菜单状态 :  1:启用   0:不启用
     */
	private Integer status;
    /**
     * 是否打开:    1:打开   0:不打开
     */
	private Integer isopen;

}
