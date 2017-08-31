package com.springBootD.test.mapper;

import org.springframework.beans.factory.annotation.Autowired;

import com.springBootD.application.demo.service.ICountryService;
import com.springBootD.application.demo.component.AsyncTask;
import org.junit.Test;

public class ThreadTest extends Base{

    @Autowired
    private ICountryService iCountryService;
    @Autowired
    private AsyncTask asyncTask;


    @Test
    public void test1() throws InterruptedException {
        iCountryService.test1();
        iCountryService.test2();
        iCountryService.test3();
        System.out.println("1111111111111111");
    }

    @Test
    public void test2(){
        asyncTask.test1();
        asyncTask.test2();
        asyncTask.test3();
    }

}
