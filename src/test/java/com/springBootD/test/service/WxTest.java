package com.springBootD.test.service;

import com.alibaba.fastjson.JSON;
import com.springBootD.Application;
import com.springBootD.wx.service.WebWxServiceTan;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
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
    private WebWxServiceTan webWxServiceTan;

    @Test
    public void testSend(){
        webWxServiceTan.login();

    }
    @Test
    public void json2Cookie(){
        String cookiestr = "{\"creationDate\":1473734164450,\"domain\":\"wx.qq.com\",\"expiryDate\":1473777364000,\"name\":\"wxuin\",\"path\":\"/\",\"persistent\":true,\"secure\":false,\"value\":\"2285935460\",\"version\":0}";

        BasicClientCookie cookie = JSON.parseObject(cookiestr, BasicClientCookie.class);
        System.out.println(cookie);
        System.out.printf(cookiestr);
        System.out.println(JSON.toJSONString(cookie));
    }

    @Test
    public void json2Cookie2(){
        String json = "{\"cookies\":[{\"creationDate\":1473734868359,\"domain\":\"wx.qq.com\",\"expiryDate\":1473778068000,\"name\":\"mm_lang\",\"path\":\"/\",\"persistent\":true,\"secure\":false,\"value\":\"zh_CN\",\"version\":0},{\"creationDate\":1473734868360,\"domain\":\"qq.com\",\"expiryDate\":1473778068000,\"name\":\"webwx_data_ticket\",\"path\":\"/\",\"persistent\":true,\"secure\":false,\"value\":\"gScNqK8TE+GCXc7QcVQxyQhV\",\"version\":0},{\"creationDate\":1473734868360,\"domain\":\"wx.qq.com\",\"expiryDate\":1789094868000,\"name\":\"webwxuvid\",\"path\":\"/\",\"persistent\":true,\"secure\":false,\"value\":\"3ce5c6fbe4a7ec986add38b683fbbb3f3090a5fe8ef43b214e783515218a82c3e32916e68c72d33f553385b381def63f\",\"version\":0},{\"creationDate\":1473734868359,\"domain\":\"wx.qq.com\",\"expiryDate\":1473778068000,\"name\":\"wxloadtime\",\"path\":\"/\",\"persistent\":true,\"secure\":false,\"value\":\"1473734868\",\"version\":0},{\"creationDate\":1473734868358,\"domain\":\"wx.qq.com\",\"expiryDate\":1473778068000,\"name\":\"wxsid\",\"path\":\"/\",\"persistent\":true,\"secure\":false,\"value\":\"3i02rhlyhLY9GDme\",\"version\":0},{\"creationDate\":1473734868353,\"domain\":\"wx.qq.com\",\"expiryDate\":1473778068000,\"name\":\"wxuin\",\"path\":\"/\",\"persistent\":true,\"secure\":false,\"value\":\"2285935460\",\"version\":0}]}\n";

        BasicCookieStore basicCookieStore = JSON.parseObject(json,BasicCookieStore.class);
        System.out.println(basicCookieStore);

    }
}
