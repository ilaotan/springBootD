package com.springBootD.test;

import java.util.List;

import org.springframework.util.Assert;

import com.google.common.collect.Lists;

/**
 * 类的描述.
 *
 * @author tan liansheng on 2017/10/16 15:45
 */
public class MainTest {

    public static void main(String[] args) {
        List<String> list = Lists.newArrayList();

        Assert.notNull(list,"test11111111111111111111111111111111");

    }

}
