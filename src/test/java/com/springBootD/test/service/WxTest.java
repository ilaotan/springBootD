package com.springBootD.test.service;

import com.springBootD.Application;
import com.springBootD.wx.service.WxService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by tan on 2016/9/7.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class WxTest {
@Autowired
    private WxService wxService;


    @Test
    public void testSend(){
//        wxService.getAllFans();

        wxService.sendMsgToSingleFan2("oQRPtt5DB0ZCAXiPPzUYGaQvMvA0","hello");
    }
}
