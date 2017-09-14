package com.springBootD.application.demo.model;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tk.mybatis.mapper.annotation.NameStyle;

/**
 * @author tan20170813
 */

@NameStyle
@Table(name = "test3")
public class Test {

    @Id
    private String id;

    private String userName;

    private String password;

    private Integer age;

    private Date updateTime;

    public Test() {
    }

    public Test(final String id, final String userName, final String password, final Integer age, final Date updateTime) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.age = age;
        this.updateTime = updateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(final Integer age) {
        this.age = age;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(final Date updateTime) {
        this.updateTime = updateTime;
    }
}
