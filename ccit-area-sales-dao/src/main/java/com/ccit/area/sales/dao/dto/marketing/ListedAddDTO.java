package com.ccit.area.sales.dao.dto.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 
 * 描述 : “挂牌新增”DTO
 * 创建人 : yn
 * 创建时间 : 2024年8月20日 下午2:26:00
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.marketing
 * 类名 : ListedAddDTO
 */
@Data
@Schema(description = "【挂牌新增】接受参数实体类")
public class ListedAddDTO {
	
	/**
	 * 挂牌子节点数组
	 */
	@NotNull(message = "至少选择一行数据")
	@Schema(description = "挂牌子节点数组")
	private List<ListedNoneAddDTO> nodeList;

	/**
	 * 组织编码
	 */
	@Length(max = 50, message = "组织编码长度不能超过50个字符")
	@Schema(description = "组织编码")
	private String orgNo;
}	
