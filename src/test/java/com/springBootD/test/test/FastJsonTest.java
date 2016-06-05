package com.springBootD.test.test;

import com.alibaba.fastjson.annotation.JSONField;
import com.springBootD.framework.utils.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Created by tan on 2016/6/5.
 */
public class FastJsonTest {


    public static void main(String[] args) {


        Test test = new Test("1","张三","123456",99);
        System.out.println(test);

        System.out.println(JsonUtils.toJSONString(test));

        Test test2 = new Test("1","张三","",99);

        System.out.println(test2);


    }


}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Test{

    private String id;

    private String userName;
//@NonNull //当字段为null时,会抛异常
    private String password;

    @JSONField(serialize = false)
    private int age;

}
