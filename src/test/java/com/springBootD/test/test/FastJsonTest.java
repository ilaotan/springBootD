package com.springBootD.test.test;

import com.alibaba.fastjson.annotation.JSONField;
import com.springBootD.framework.utils.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by tan on 2016/6/5.
 */
public class FastJsonTest {


    public static void main(String[] args) {


        Test test = new Test("1","张三","123456",99);

        System.out.println(test);

        System.out.println(JsonUtils.toJSONString(test));

    }


}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Test{

    private String id;

    private String userName;

    private String password;

    @JSONField(serialize = false)
    private int age;

}
