package com.ccit.area.sales.dao.vo.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “组织树列表”VO
 * 创建人 : yn
 * 创建时间 : 2024年9月25日 下午3:03:25
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.vo.system
 * 类名 : SystemOrgTreeVO
 */
@Data
@Schema(description = "【组织树列表】返回结果实体类")
public class SystemOrgTreeVO {

    /**
     * 组织ID
     */
    @Schema(description = "组织ID")
    private Long id;

    /**
     * 组织编码
     */
    @Schema(description = "组织编码")
    private String orgNo;

    /**
     * 组织名称
     */
    @Schema(description = "组织名称")
    private String orgName;
    
    /**
     * 组织类型
     */
	@Schema(description = "组织类型")
    private String orgType;
	
    /**
     * 组织类型名称
     */
	@Schema(description = "组织类型名称")
    private String orgTypeName;

    /**
     * 组织排序
     */
    @Schema(description = "组织排序")
    private Integer orgOrders;

    /**
     * 状态：0-正常；1-禁用；
     */
    @Schema(description = "状态：0-正常；1-禁用；")
    private Integer status;
    
    /**
     * 上级ID
     */
    @Schema(description = "上级ID")
    private Long parentId;
    
    /**
     * 是否有叶子节点
     */
	@Schema(description = "是否有叶子节点")
    private Boolean orgIsLeaf = false;
    
    /**
     * 子组织集合
     */
	@Schema(description = "子组织集合")
    private List<SystemOrgTreeVO> children = new ArrayList<>();

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtModified;

}
