package com.springBootD.application.demo.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo extends BaseEntity {

    private String username;

    private String password;

    private String usertype;

    private Integer enabled;

    private String qq;

    private String email;

    private String tel;

}
