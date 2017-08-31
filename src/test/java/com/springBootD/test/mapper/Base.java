package com.springBootD.test.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.springBootD.Application;
import org.junit.runner.RunWith;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class Base {

    public final Logger logger = LoggerFactory.getLogger(this.getClass());
}
