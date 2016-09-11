package com.springBootD.wx.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.springBootD.wx.kit.HttpClientKit;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.entity.StringEntity;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.dom.DOMDocument;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tan on 2016/9/11.
 */

@Service
public class WebWxServiceTan {

    private static final Log logger = LogFactory.getLog(WebWxServiceTan.class);

    private String jsLoginUrl = "https://login.weixin.qq.com/jslogin";
    private String qrUrl = "https://login.weixin.qq.com/qrcode/";
    private String checkQrLogin = "https://login.weixin.qq.com/cgi-bin/mmwebwx-bin/login";


    private String Skey="";
    private String Sid="";
    private String Uin="";
    private String DeviceID="";

    private Map<String,String> baseRequest= Maps.newHashMap();

    /**
     *  step 1 获得登录参数必填的uuid
     *
     * @return
     */
    private String getUuid(){
        Map<String,String> params = Maps.newHashMap();
        params.put("appid","wx782c26e4c19acffb");
        params.put("redirect_uri","https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxnewloginpage");
        params.put("fun","new");
        params.put("lang","zh_CN");
        params.put("_",new Date().getTime()+"");

        String res = HttpClientKit.doGet(jsLoginUrl,params);
        Pattern p=Pattern.compile("window.QRLogin.code = (\\d+); window.QRLogin.uuid = \"(\\S+?)\";");
        Matcher m=p.matcher(res);
        String returnStr="";
        while(m.find()) {
//            System.out.println(m.group(1));
            returnStr = m.group(2);
        }
        return returnStr;
    }

    private void getQrcode(String uuid,String filePath){
        String url = qrUrl+uuid;
        HttpClientKit.doGetFile(url,null,filePath);
    }


    private String checkQrLogin(String uuid){
        Map<String,String> params = Maps.newHashMap();
        params.put("loginicon","true");
        params.put("uuid",uuid);
        params.put("tip","0");
        params.put("_",new Date().getTime()+"");

        String res = HttpClientKit.doGet(checkQrLogin,params);
        return res;
    }

    private void setAfterLoginVal(String res){
        res = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> "+res;
        try {
            Document document = DocumentHelper.parseText(res);
            Element root = document.getRootElement();
            List<Element> childElements = root.elements();
            for(Element element:childElements){
                if(element.getName().equals("skey")){
                    this.Skey = element.getStringValue();
                }else if(element.getName().equals("wxsid")){
                    this.Sid = element.getStringValue();
                }else if(element.getName().equals("wxuin")){
                    this.Uin = element.getStringValue();
                }else if(element.getName().equals("pass_ticket")){
                    this.DeviceID = element.getStringValue();
                }
            }

            baseRequest.put("Skey",Skey);
            baseRequest.put("Sid",Skey);
            baseRequest.put("Uin",Uin);
            baseRequest.put("DeviceID",DeviceID);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }



    public void login(){

        String uuid = getUuid();
        logger.info(" 获得uuid-->"+uuid);
        String filePath="E:\\qr.jpg";
        getQrcode(uuid,filePath);
        logger.info("二维码已输出到---> "+filePath);
        logger.info("二维码已输出到---> "+qrUrl+uuid);

        String code ="";
        String res = "";
        Pattern p;
        Matcher m;
        while (true){
            logger.info("开始循环");
            res = checkQrLogin(uuid);
            logger.info("开始检验二维码是否被扫描"+res);
            p = Pattern.compile("window.code=(\\d+)");
            m = p.matcher(res);
            if(m.find()){
                code = m.group(1);
            }else{
                logger.error("正则截取出错1111");
            }
            //200:登陆成功 201:扫描成功 408:图片过期
            if(code.equals("200")){
                logger.info("登录成功");
                p = Pattern.compile("window.redirect_uri=\"(\\S+)\";");
                logger.info("成功后的信息是"+res);

                m = p.matcher(res);
                if(m.find()){
                    res = m.group(1);
                }else{
                    logger.error("正则截取出错2222");
                }
                logger.info("成功后需redirect的地址是:"+res);
                setAfterLoginVal(HttpClientKit.doGetAndNoRedirectSaveCookie(res,null));
                break;
            }else if(code.equals("201")){
                logger.info("扫码成功,等待手机端确认ing..... 睡一秒");
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {}
            }else if(code.equals("408")){
                logger.equals("图片过期");
                continue;
            }
        }

        logger.info("测试登录后");

        //                private String Skey="";
//                private String Sid="";
//                private String Uin="";
//                private String DeviceID="";

        Map map = Maps.newHashMap();
        map.put("BaseRequest", baseRequest);


        StringEntity entity = new StringEntity(JSON.toJSONString(map),"utf-8");//解决中文乱码问题

        String wewxIntUrl = "https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxinit?r="+new Date().getTime()+"&pass_ticket="+DeviceID;
        String res3 = HttpClientKit.doPost(wewxIntUrl,entity);
        System.out.println(res3);
        if(StringUtils.isNotBlank(res3)){
            JSONObject userInfo = JSON.parseObject(res3);
            JSONObject user = userInfo.getJSONObject("User");
            System.out.println(user.getString("UserName"));
        }

        String sendMsgUrl = "https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxsendmsg";
        Map msgMap = Maps.newHashMap();
        msgMap.put("Type",1);
        msgMap.put("Content","你好 大傻逼");
        msgMap.put("FromUserName","@cebf84435a266ae1ca1f1151d50a8693");
        msgMap.put("ToUserName","@cebf84435a266ae1ca1f1151d50a8693");
        msgMap.put("LocalID",new Date().getTime());
        msgMap.put("ClientMsgId",new Date().getTime());

        map.put("Msg",msgMap);

        String resdd = HttpClientKit.doPost(sendMsgUrl,new StringEntity(JSON.toJSONString(map),"utf-8"));
        System.out.println("傻逼了吗"+resdd);

    }

}
