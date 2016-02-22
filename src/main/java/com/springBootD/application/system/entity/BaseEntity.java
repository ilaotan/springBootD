/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.springBootD.application.system.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity {
	/**状态有效*/
	public static final int STATUS_VALID = 1;
	/**状态无效*/
	public static final int STATUS_NO_VALID = 0;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected long id;
	//0:无效 1:有效
	protected int status;
	//创建信息
	protected long createById;
	protected Date createDate;
	protected long updateById;
	protected Date updateDate;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getCreateById() {
		return createById;
	}
	public void setCreateById(long createById) {
		this.createById = createById;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public long getUpdateById() {
		return updateById;
	}
	public void setUpdateById(long updateById) {
		this.updateById = updateById;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}	
}
