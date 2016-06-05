package com.springBootD.test.test;

import com.alibaba.fastjson.annotation.JSONField;
import com.springBootD.framework.utils.JsonUtils;

/**
 * Created by tan on 2016/6/5.
 */
public class FastJsonTest {


    public static void main(String[] args) {


        System.out.println(JsonUtils.toJSONString(new Test("1","张三","123456",99)));
    }


}

class Test{

    public Test(String id, String userName, String password, int age) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.age = age;
    }

    private String id;

    private String userName;

    private String password;

    @JSONField(serialize = false)
    private int age;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
