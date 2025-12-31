package com.ccit.area.sales.rpc.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.ccit.area.sales.rpc.dto.UserDTO;


/**
 * 外部接口调用demo
 *
 * @author: chinacoalit.com
 * @date 2024/1/9 15:50
 * @version: v1.0 初始化
 */
@FeignClient(name = "userFegin", url = "${user.center.host}")
public interface UserFegin {

    /**
     * 根据id获取用户信息
     * @param userId 用户id
     * @return com.ccit.area.sales.rpc.dto.UserDTO
     * @exception
     * @author chinacoalit.com
     * @date 2024/1/9 15:44
     */
    @GetMapping("/api/getuserinfobyid/{userId}")
    UserDTO getUserInfoById(@PathVariable("userId") String userId);
}
