package com.springBootD.application.demo.service;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mpush.api.push.PushContext;
import com.mpush.api.push.PushSender;
import com.mpush.api.srd.ServiceDiscovery;
import com.mpush.api.srd.ServiceNames;
import com.mpush.api.srd.ServiceNode;
import com.mpush.client.MPushClient;
import com.mpush.common.user.UserManager;
import org.apache.curator.retry.RetryUntilElapsed;

//@Service
public class MPushManager {

    @Resource
    private ServiceDiscovery serviceDiscovery;

    private UserManager userManager;

    @Resource
    private MPushClient mPushClient;

    @Resource
    private PushSender mpusher;


    @PostConstruct
    public void init() {
        userManager = new UserManager(mPushClient.getCachedRemoteRouterManager());
    }

    public Collection<ServiceNode> getConnectServerList() {
        return serviceDiscovery.lookup(ServiceNames.CONN_SERVER);
    }

    public long getOnlineUserNum(String serverIp) {
        return userManager.getOnlineUserNum(serverIp);
    }

    public void kickUser(String userId) {
        userManager.kickUser(userId);
    }

    public void send(PushContext pushContext){
        mpusher.send(pushContext);
    }
}
