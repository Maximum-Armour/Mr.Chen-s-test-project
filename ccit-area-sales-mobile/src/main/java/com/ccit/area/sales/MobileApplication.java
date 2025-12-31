package com.ccit.area.sales;


import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * 移动端主启动类 入口
 *
 * @author: chinacoalit.com
 * @date 2024/5/30 15:58
 * @version: v1.0 初始化
 */
@MapperScan("com.ccit.area.sales.mapper")
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients({"com.ccit"})
public class MobileApplication {

    /**
     * 日志记录器
     */
    private static final Logger LOG = LoggerFactory.getLogger(MobileApplication.class);

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext context = SpringApplication.run(MobileApplication.class, args);
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
