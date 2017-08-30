package com.springBootD.application.system.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;

/**
 * 字典表
 */

@Getter
@Setter
@Table(name = "")
public class Dict extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 排序
     */
	private Integer num;
    /**
     * 父级字典
     */
	private Integer pid;
    /**
     * 名称
     */
	private String name;
    /**
     * 提示
     */
	private String tips;

}
