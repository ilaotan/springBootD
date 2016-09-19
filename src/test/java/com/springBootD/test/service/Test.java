package com.springBootD.test.service;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by tan on 2016/9/16.
 */
public class Test {


    public static void main(String[] args) {

        ModelA modelA = new ModelA("123","A");
        ModelC modelC = new ModelC("456","C");

        ModelAll modelAll = new ModelAll();

        try {
            BeanUtils.copyProperties(modelAll,modelA);
            BeanUtils.copyProperties(modelAll,modelC);

            System.out.println(modelAll);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }
}
