package com.ccit.area.sales.dao.vo.marketing;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 *
  * 描述 : “流程操作分页列表”VO
  * 创建人 : tb
  * 创建时间 :2024年9月26日 上午10:26:55
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.vo.bidding
  * 类名 : ProcessOperationPageListVO
 */
@Data
@Schema(description = "【流程操作分页列表】返回结果实体类")
public class ProcessOperationPageListVO {

    /**
     * 流程
     */
    @Schema(description = "流程")
    private String technologicalProcess;

    /**
     * 操作者
     */
    @Schema(description = "操作者")
    private String operatorName;

    /**
     * 区域名称
     */
    @Schema(description = "区域名称")
    private Date operatorTime;

    /**
     * 操作
     */
    @Schema(description = "操作")
    private String operation;
}
