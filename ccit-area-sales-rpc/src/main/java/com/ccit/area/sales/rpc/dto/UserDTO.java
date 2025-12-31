package com.ccit.area.sales.rpc.dto;

import lombok.Data;

/**
 * 外部接口调用DTO类
 *
 * @author: chinacoalit.com
 * @date 2024/1/9 15:50
 * @version: v1.0 初始化
 */
@Data
public class UserDTO {

    /**
     * 主键
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 地址
     */
    private String address;
}
