package com.ccit.area.sales.web;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 主启动类  入口
 *
 * @author: chinacoalit.com
 * @date 2024/1/9 15:58
 * @version: v1.0 初始化
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients({ "com.ccit" })
@MapperScan("com.ccit.area.sales.dao.mapper")
@ComponentScan(value = "com.ccit.area.sales")
public class WebApplication {

    /**
     * 日志记录器
     */
    private static final Logger LOG = LoggerFactory.getLogger(WebApplication.class);

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext context = SpringApplication.run(WebApplication.class, args);
        ConfigurableEnvironment env = context.getEnvironment();
        String port = env.getProperty("server.port");
        String ip = InetAddress.getLocalHost().getHostAddress();
        String path = env.getProperty("server.servlet.context-path");
        if (StringUtils.isBlank(path)) {
            path = "";
        }
        LOG.info("\n----------------------------------------------------\n"
            +"\tApplication is running! Access address:\n"
            +"\tLocal:\t\thttp://localhost:{}{}\n"
            +"\tExternal:\thttp://{}:{}{}\n"
            +"----------------------------------------------------\n", port, path, ip, port, path);
    }

}