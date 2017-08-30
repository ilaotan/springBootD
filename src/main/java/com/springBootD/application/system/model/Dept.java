package com.springBootD.application.system.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;

/**
 * 部门表
 */

@Getter
@Setter
@Table(name = "system_dept")
public class Dept extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 排序
     */
	private Integer num;
    /**
     * 父部门id
     */
	private Integer pid;
    /**
     * 父级ids
     */
	private String pids;
    /**
     * 简称
     */
	private String simplename;
    /**
     * 全称
     */
	private String fullname;
    /**
     * 提示
     */
	private String tips;
    /**
     * 版本（乐观锁保留字段）
     */
	private Integer version;

}
