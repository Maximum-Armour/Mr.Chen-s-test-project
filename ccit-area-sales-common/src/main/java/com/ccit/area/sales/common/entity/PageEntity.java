package com.ccit.area.sales.common.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : 公共实体包装类
 * 创建人 : yn
 * 创建时间 : 2024年6月10日 下午5:01:32
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.common.entity
 * 类名 : PageEntity
 */
@Data
@Schema(description = "公共实体包装类")
public class PageEntity<T> {

	/**
	 * 分页页码
	 */
	@Schema(description = "分页页码", example = "【全量分页页面:0,默认分页页码:1】")
	private Integer page = 1;

	/**
	 * 分页步长
	 */
	@Schema(description = "分页步长", example = "【全量分页步长:0,默认分页步长:10】")
	private Integer size = 10;

	/**
	 * “T”是泛型的默认值，可以被任意类型所代替
	 */
	private T param;

}
