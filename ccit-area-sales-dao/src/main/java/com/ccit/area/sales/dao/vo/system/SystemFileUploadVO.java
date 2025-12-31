package com.ccit.area.sales.dao.vo.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “上传附件”VO
 * 创建人 : yn
 * 创建时间 : 2024年10月29日 下午2:07:25
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.vo.system
 * 类名 : SystemFileUploadVO
 */
@Data
@Schema(description = "【上传附件】返回结果实体类")
public class SystemFileUploadVO {

	/**
     * 附件ID
     */
	@Schema(description = "附件ID")
    private Long id;
    
    /**
     * 附件地址
     */
	@Schema(description = "附件地址")
    private String filePath;
	
}
