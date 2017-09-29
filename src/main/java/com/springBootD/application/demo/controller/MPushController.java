package com.springBootD.application.demo.controller;


import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.JSONScanner;
import com.mpush.api.Constants;
import com.mpush.api.push.MsgType;
import com.mpush.api.push.PushCallback;
import com.mpush.api.push.PushContext;
import com.mpush.api.push.PushMsg;
import com.mpush.api.push.PushSender;
import com.mpush.api.router.ClientLocation;
import com.mpush.api.srd.ServiceDiscovery;
import com.mpush.api.srd.ServiceNames;
import com.mpush.api.srd.ServiceNode;
import com.mpush.tools.Jsons;
import com.springBootD.application.demo.model.NotifyDO;
import com.springBootD.application.demo.model.OfflineMsg;
import com.springBootD.application.demo.service.MPushManager;
import org.apache.commons.lang3.StringUtils;

//@RestController
//@RequestMapping("admin")
public class MPushController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AtomicLong msgIdSeq = new AtomicLong(1);//TODO 业务自己处理


    @Resource
    private MPushManager mPushManager;


    @GetMapping("push")
    public String push(String userId,String message){

        NotifyDO notifyDO = new NotifyDO(message);


        PushMsg pushMsg = PushMsg.build(MsgType.NOTIFICATION_AND_MESSAGE, Jsons.toJson(notifyDO));
        pushMsg.setMsgId(Long.toString(msgIdSeq.incrementAndGet()));
        byte[] content = Jsons.toJson(pushMsg).getBytes(Constants.UTF_8);

        doSend(userId, content, new PushCallback() {
            int retryCount = 0;

            @Override
            public void onSuccess(String userId, ClientLocation location) {
                logger.warn("send msg success");
            }

            @Override
            public void onFailure(String userId, ClientLocation clientLocation) {
                saveOfflineMsg(new OfflineMsg(userId, content));
            }

            @Override
            public void onOffline(String userId, ClientLocation clientLocation) {
                if (clientLocation != null) {
                    String os = clientLocation.getOsName().toLowerCase();
                    if (os.contains("ios")) {
                        send2ANPs(userId, notifyDO, clientLocation.getDeviceId());
                    } else if (os.contains("android")) {
                        if (os.contains("xiaomi")) {
                            send2MiPush(userId, notifyDO);
                        } else if (os.contains("huawei")) {
                            send2HuaweiPush(userId, notifyDO);
                        } else {
//                            send2JPush(userId, notifyDO);
                            saveOfflineMsg(new OfflineMsg(userId, content));
                        }
                    } else {
                        saveOfflineMsg(new OfflineMsg(userId, content));
                    }
                } else {
                    saveOfflineMsg(new OfflineMsg(userId, content));
                }
            }

            @Override
            public void onTimeout(String userId, ClientLocation clientLocation) {
                if (retryCount++ > 1) {
                    saveOfflineMsg(new OfflineMsg(userId, content));
                } else {
                    doSend(userId, content, this);
                }
            }
        });

        return "ok!!!!!!";
    }

    @GetMapping("/list/servers")
    public String listServers(){
        return JSON.toJSONString(mPushManager.getConnectServerList());
    }

    @GetMapping("/get/onlineUserNum")
    public String getOnlineUserNum(String ip){

        if(StringUtils.isBlank(ip)){
            return "请传递ip参数";
        }
        return JSON.toJSONString(mPushManager.getOnlineUserNum(ip));
    }

    private void doSend(String userId, byte[] content, PushCallback callback) {
        mPushManager.send(new PushContext(content)
                .setUserId(userId)
                .setCallback(callback)
        );
    }

    private void send2ANPs(String userId, NotifyDO notifyDO, String deviceToken) {
        logger.info("send to ANPs");
    }

    private void send2MiPush(String userId, NotifyDO notifyDO) {
        logger.info("send to xiaomi push");
    }

    private void send2HuaweiPush(String userId, NotifyDO notifyDO) {
        logger.info("send to huawei push");
    }

    private void send2JPush(String userId, NotifyDO notifyDO) {
        logger.info("send to jpush");
    }

    private void saveOfflineMsg(OfflineMsg offlineMsg) {
        logger.info("save offline msg to db {}",JSON.toJSONString(offlineMsg));
    }


}
