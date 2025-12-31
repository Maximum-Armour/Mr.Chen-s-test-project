package com.ccit.area.sales.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 移动端控制器
 *
 * @author: chinacoalit.com
 * @date 2024/5/30 10:19
 * @version: v1.0 初始化
 */
@RestController
@RequestMapping("/webapi")
public class MobileController {


    @GetMapping("/hellomobile")
    public String helloMobile(){
        return "您好，移动端";
    }
}
