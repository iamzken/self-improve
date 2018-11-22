package com.gupaoedu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: qingshan
 * @Date: 2018/10/14 11:27
 * @Description: 咕泡学院，只为更好的你
 *
 * 测试从配置中心服务端（最终是从git仓库）读取配置
 *
 */
@RestController
@RefreshScope
public class ConfigController {

    private static Logger logger = LoggerFactory.getLogger(ConfigController.class);

    @Value("${changsha.pilot.name}")
    String message;

    @RequestMapping("/getConfig")
    public String getPropertyFromConfigServer() {
        String msg = "message from config server : " + message ;
        logger.info(msg);
        return msg;
    }

}
