package com.ccit.area.sales.dao.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “验证码”VO
 * 创建人 : yn
 * 创建时间 : 2024年7月8日 上午10:07:32
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.common
 * 类名 : VerifyCodeVO
 */
@Data
@Schema(description = "【验证码】返回结果实体类")
public class VerifyCodeVO {

	/**
	 * 图片数组
	 */
	@Schema(description = "图片数组")
    private byte[] imgBytes;
	
}
