package com.springBootD.application.demo.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.springBootD.application.demo.model.vo.AbstractResult;
import com.springBootD.framework.utils.UuidUtils;

/**
 * 类的描述.
 *
 * @author tan liansheng on 2017/10/17 15:50
 */

@RestController
@RequestMapping("park")
public class DcpController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("token")
    public String token(@RequestBody Map<String,String> param){

        logger.error("接收[token]请求" + JSON.toJSONString(param));

        AbstractResult abstractResult = new AbstractResult("100","success");
        Map<String,String> content = Maps.newHashMap();
        content.put("token", UuidUtils.newid());
        content.put("expire","900");
        abstractResult.setContent(content);
        return JSON.toJSONString(abstractResult);
    }

    @RequestMapping("hpcamera")
    public String hpcamera(@RequestBody Map<String,String> param){

        logger.error("接收[hpcamera]请求" + JSON.toJSONString(param));
        String res = getCommonReturn(param.get("flowId"));;
        logger.error("接收[hpcamera]请求,返回响应 " + res);
        return res;
    }
    @RequestMapping("msensor")
    public String msensor(@RequestBody Map<String,String> param){

        logger.error("接收[msensor]请求" + JSON.toJSONString(param));
        String res = getCommonReturn(param.get("flowId"));
        logger.error("接收[msensor]请求,返回响应 " + res);
        return res;
    }


    private String getCommonReturn(String flowId){
        AbstractResult abstractResult = new AbstractResult("100","success");
        Map<String,String> content = Maps.newHashMap();
        content.put("flowId", flowId);
        abstractResult.setContent(content);
        return JSON.toJSONString(abstractResult);
    }

}
