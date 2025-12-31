package com.ccit.area.sales.dao.vo.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “当前登录用户令牌”VO
 * 创建人 : yn
 * 创建时间 : 2024年10月26日 下午8:49:49
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.vo.system
 * 类名 : SystemCurrentUserTokenVO
 */
@Data
@Schema(description = "【当前登录用户令牌】返回结果实体类")
public class SystemCurrentUserTokenVO {

    /**
     * 密码是否已过期：0-否；1-是；
     */
	@Schema(description = "密码是否已过期：0-否；1-是；")
    private Integer passwordExpired;
	
}
