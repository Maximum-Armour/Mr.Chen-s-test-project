package com.ccit.area.sales.web.controller.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ccit.area.sales.common.result.ResponseVO;
import com.ccit.area.sales.dao.dto.common.VerifyCodeVO;
import com.ccit.area.sales.service.common.ICommonService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 
 * 描述 : “基础管理”前端控制器
 * 创建人 : yn
 * 创建时间 : 2024年7月8日 上午10:04:13
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.web.controller.common
 * 类名 : CommonController
 */
@RestController
@Tag(name = "基础管理")
@RequestMapping(value = "/webapi/common")
public class CommonController {

	/**
	 * “基础”服务类
	 */
	@Autowired
    private ICommonService commonService;
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月8日 下午2:38:44
	 * 描述 : 获取验证码
	 * 包名 : com.ccit.area.sales.web.controller.common
	 * 方法名 : getVerifyCode
	 *  ResponseVO<VerifyCodeVO>  
	 *  @throws
	 */
	@Operation(summary = "获取验证码", description = "使用说明【img标签src=data:image/jpg;base64,图片数组值】")
	@Parameters({
		@Parameter(in = ParameterIn.QUERY, name = "key", description = "UUID或者时间戳", 
				required = true, schema = @Schema(type = "String"))
	})
    @GetMapping(value = "/getVerifyCode")
    public ResponseVO<VerifyCodeVO> getVerifyCode(@RequestParam("key") String key) {
		return ResponseVO.success(commonService.generateVerifyCode(key, 4));
    }
	
}
