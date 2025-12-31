package com.ccit.area.sales.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 外部系统开放的接口，需要经过安全认证才可调用
 *
 * @author: chinacoalit.com
 * @date 2024/1/9 15:51
 * @version: v1.0 初始化
 */
@RestController
@RequestMapping("/openapi")
public class OpenApiController {

    @GetMapping("/helloopenapi")
    public String helloOpenApi(){
        return "你好，openapi";
    }
}
