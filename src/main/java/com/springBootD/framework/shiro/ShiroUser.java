package com.springBootD.framework.shiro;


import java.io.Serializable;


public class ShiroUser implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String roleid;
	private String name;
	private String account;
	private String email;
	private String sex;
	private String code;
	private String phone;

	public ShiroUser() {

	}

	public ShiroUser(String id, String roleid, String name, String account) {
		this.id = id;
		this.roleid = roleid;
		this.name = name;
		this.account = account;
	}

	public ShiroUser(String id, String roleid, String name, String account, String sex, String email, String code, String phone) {
		this.id = id;
		this.roleid = roleid;
		this.name = name;
		this.account = account;
		this.sex = sex;
		this.email = email;
		this.code = code;
		this.phone = phone;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 本函数输出将作为默认的<shiro:principal/>输出.
	 */
	@Override
	public String toString() {
		return this.account;
	}

	/**
	 * 重载equals,只计算account;
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShiroUser other = (ShiroUser) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		return true;
	}
}