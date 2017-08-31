package com.springBootD.application.demo.component;


import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AsyncTask {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static Random random =new Random();



    @Async("myTaskAsyncPool")
    public void test1(){
        try {
            Thread.sleep(random.nextInt(10000));
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.error("test1");
    }
    @Async("myTaskAsyncPool")
    public void test2(){
        try {
            Thread.sleep(random.nextInt(10000));
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.error("test2");
    }
    @Async("myTaskAsyncPool")
    public void test3(){
        try {
            Thread.sleep(random.nextInt(10000));
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.error("test3");
    }

}
