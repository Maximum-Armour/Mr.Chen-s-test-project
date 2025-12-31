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
 * 描述 : “菜单树列表”VO
 * 创建人 : yn
 * 创建时间 : 2024年6月15日 下午9:28:20
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.vo.system
 * 类名 : SystemMenuTreeVO
 */
@Data
@Schema(description = "【菜单树列表】返回结果实体类")
public class SystemMenuTreeVO {
	
	/**
	 * 菜单ID
	 */
	@Schema(description = "菜单ID")
	private Long id;

	/**
	 * 菜单图标
	 */
	@Schema(description = "菜单图标")
	private String menuIcon;
	
    /**
     * 菜单名称
     */
	@Schema(description = "菜单名称")
    private String menuName;
	
	/**
	 * 菜单路由
	 */
	@Schema(description = "菜单路由")
	private String menuRouter;
	
    /**
     * 菜单地址
     */
	@Schema(description = "菜单地址")
    private String menuPath;

    /**
     * 父级ID
     */
	@Schema(description = "父级ID")
    private Long parentId;
	
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
     * 菜单类型：1-目录；2-菜单；3-按钮；
     */
	@Schema(description = "菜单类型：1-目录；2-菜单；3-按钮；")
    private Integer menuType;
	
    /**
     * 是否有叶子节点
     */
	@Schema(description = "是否有叶子节点")
    private Boolean menuIsLeaf = false;
    
    /**
     * 子菜单集合
     */
	@Schema(description = "子菜单集合")
    private List<SystemMenuTreeVO> children = new ArrayList<>();
	
    /**
     * 菜单权限
     */
	@Schema(description = "菜单权限")
    private String menuPurview;
	
    /**
     * 菜单排序
     */
	@Schema(description = "菜单排序")
    private Integer menuOrders;
	
	/**
	 * 最大排序号
	 */
	@Schema(description = "最大排序号")
	private Integer maxOrders = 0;
	
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
