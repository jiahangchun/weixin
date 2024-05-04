package com.github.binarywang.demo.wx.cp.controller.config;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("config")
public class ConfigController {

    @NacosValue(value = "${useLocalCache:false}", autoRefreshed = true)
    private boolean useLocalCache;


    @Value("${test.name}")
    private String userName;

    @Value("${wechat.cp.appConfigs.agentId}")
    private String agentId;


    @RequestMapping(value = "/getWechat", method = GET)
    @ResponseBody
    public String getParam() {
        return agentId;
    }


    @RequestMapping(value = "/getCache", method = GET)
    @ResponseBody
    public boolean get() {
        return useLocalCache;
    }
}
