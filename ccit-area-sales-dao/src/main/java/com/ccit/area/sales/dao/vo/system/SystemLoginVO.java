package com.ccit.area.sales.dao.vo.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “登录”VO
 * 创建人 : yn
 * 创建时间 : 2024年7月4日 上午11:14:50
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.vo.system
 * 类名 : SystemLoginVO
 */
@Data
@Schema(description = "【登录】返回结果实体类")
public class SystemLoginVO {

	/**
	 * 令牌值
	 */
	@Schema(description = "令牌值")
	private String token;
	
}
