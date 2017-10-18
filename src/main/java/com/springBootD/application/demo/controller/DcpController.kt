package com.springBootD.application.demo.controller

import org.slf4j.LoggerFactory

import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import com.alibaba.fastjson.JSON
import com.google.common.collect.Maps
import com.springBootD.application.demo.model.vo.AbstractResult
import com.springBootD.framework.utils.UuidUtils

/**
 * 类的描述.
 *
 * @author tan liansheng on 2017/10/17 15:50
 */

@RestController
@RequestMapping("park")
class DcpController {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @RequestMapping("token")
    fun token(@RequestBody param: Map<String, String>): String {

        logger.error("接收[token]请求" + JSON.toJSONString(param))

        val abstractResult = AbstractResult("100", "success")
        val content = Maps.newHashMap<String, String>()
        content.put("token", UuidUtils.newid())
        content.put("expire", "900")
        abstractResult.content = content
        return JSON.toJSONString(abstractResult)
    }

    @RequestMapping("hpcamera")
    fun hpcamera(@RequestBody param: Map<String, String>): String {

        logger.error("接收[hpcamera]请求" + JSON.toJSONString(param))
        val res = getCommonReturn(param["flowId"])
        logger.error("接收[hpcamera]请求,返回响应 " + res)
        return res
    }

    @RequestMapping("msensor")
    fun msensor(@RequestBody param: Map<String, String>): String {

        logger.error("接收[msensor]请求" + JSON.toJSONString(param))
        val res = getCommonReturn(param["flowId"])
        logger.error("接收[msensor]请求,返回响应 " + res)
        return res
    }


    private fun getCommonReturn(flowId: String?): String {
        val abstractResult = AbstractResult("100", "success")
        val content = Maps.newHashMap<String, String>()
        content.put("flowId", flowId)
        abstractResult.content = content
        return JSON.toJSONString(abstractResult)
    }

}
