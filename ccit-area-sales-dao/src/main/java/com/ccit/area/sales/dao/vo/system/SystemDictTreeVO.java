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
 * 描述 : “字典树结构”VO
 * 创建人 : yn
 * 创建时间 : 2024年6月16日 上午9:29:45
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.vo.system
 * 类名 : SystemDictTreeVO
 */
@Data
@Schema(description = "【字典树结构】返回结果实体类")
public class SystemDictTreeVO {

	/**
     * 字典ID
     */
	@Schema(description = "字典ID")
    private Long id;
	
    /**
     * 字典编码
     */
	@Schema(description = "字典编码")
    private String dictCode;

    /**
     * 字典名称
     */
	@Schema(description = "字典名称")
    private String dictName;

    /**
     * 字典值
     */
    @Schema(description = "字典值")
    private String dictValue;

    /**
     * 上级ID
     */
	@Schema(description = "上级ID")
    private Long parentId;
	
    /**
     * 是否有叶子节点
     */
	@Schema(description = "是否有叶子节点")
    private Boolean dictIsLeaf = false;
    
    /**
     * 子菜单集合
     */
	@Schema(description = "子菜单集合")
    private List<SystemDictTreeVO> children = new ArrayList<>();
	
    /**
     * 状态：0-正常；1-禁用；
     */
	@Schema(description = "状态：0-正常；1-禁用；")
    private Integer status;
	
    /**
     * 状态名称
     */
	@Schema(description = "状态名称")
    private String statusName;
	
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
