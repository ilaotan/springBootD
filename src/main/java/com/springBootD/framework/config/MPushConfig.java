package com.springBootD.framework.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mpush.api.spi.common.ServiceDiscoveryFactory;
import com.mpush.api.srd.ServiceDiscovery;
import com.mpush.client.MPushClient;
import com.mpush.client.push.PushClient;

//@Configuration
public class MPushConfig {


    @Bean(initMethod = "start",destroyMethod = "stop")
    public ServiceDiscovery serviceDiscovery(){
        return ServiceDiscoveryFactory.create();
    }

    @Bean
    public MPushClient mPushClient(){
        return new MPushClient();
    }

    @Bean(initMethod = "start",destroyMethod = "stop")
    public PushClient pushSender(MPushClient mPushClient){
        PushClient pushClient = new PushClient();
        pushClient.setMPushContext(mPushClient);
        return pushClient;
    }


}
