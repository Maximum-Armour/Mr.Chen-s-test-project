package com.ccit.area.sales.common.aop.annotaion.enums;

/**
 * 
 * 描述 : 业务操作类型枚举类
 * 创建人 : yn
 * 创建时间 : 2024年7月9日 下午3:33:50
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.common.aop.enums
 * 类名 : BusinessType
 */
public enum BusinessType {

	/**
     * 其它
     */
    OTHER,
    
    /**
     * 查询
     */
    SELECT,

    /**
     * 新增
     */
    INSERT,
    
    /**
     * 详情
     */
    GET,

    /**
     * 修改
     */
    EDIT,

    /**
     * 删除
     */
    DELETE,
    
    /**
     * 导入
     */
    IMPORT,

    /**
     * 导出
     */
    EXPORT,

    /**
     * 保存/更新
     */
    SAVEORUPDATE
	
}
