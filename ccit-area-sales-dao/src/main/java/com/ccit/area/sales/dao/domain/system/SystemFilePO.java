package com.ccit.area.sales.dao.domain.system;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ccit.area.sales.dao.domain.BasePO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 
 * 描述 : “附件”实体类
 * 创建人 : yn
 * 创建时间 : 2024年10月29日 上午9:52:31
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.domain.system
 * 类名 : SystemFilePO
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_system_file")
public class SystemFilePO extends BasePO<SystemFilePO> {
	
	private static final long serialVersionUID = 1L;
	
	/**
     * 附件名称
     */
    @TableField(value = "file_name")
	private String fileName;
	
    /**
     * 附件地址
     */
    @TableField(value = "file_path")
	private String filePath;
	
    /**
     * 附件后缀
     */
    @TableField(value = "file_suffix")
	private String fileSuffix;
	
    /**
     * 附件大小
     */
    @TableField(value = "file_size")
	private String fileSize;
    
    /**
	 * MinIO分片号
	 */
	@TableField(value = "minio_etag")
	private String minioEtag;
	
    /**
     * 业务ID
     */
    @TableField(value = "business_id")
	private String businessId;
	
    /**
     * 业务编码
     */
    @TableField(value = "business_code")
	private String businessCode;
    
	/**
     * 
     * 创建人 : yn
     * 创建时间 : 2024年10月29日 上午09:57:37
     * 构造方法名 : SystemFilePO()
     * 描述 : 
     */
	public SystemFilePO() {
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年10月29日 上午09:57:37
	 * 构造方法名 : SystemFilePO(Boolean deleted, Boolean isAutoFillUser)
	 * 参数 :	deleted			删除标识：true-已删除；false-未删除；
     *		isAutoFillUser	是否自动填充用户：true-是；false-否；
	 */
	public SystemFilePO(Boolean deleted, Boolean isAutoFillUser) {
    	super.setDeleted(deleted ? 1 : 0);
    	super.setDeletedTime(new Date());
    	if (isAutoFillUser) {
    		super.setDeletedByName(null);
    		super.setDeletedTime(null);
    	}
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年10月29日 上午09:57:37
	 * 描述 : 包装器
	 * 包名 : com.ccit.area.sales.dao.model
	 * 方法名 : wrapper
	 *  LambdaQueryWrapper<SystemFilePO>  
	 *  @throws
	 */
	public static LambdaQueryWrapper<SystemFilePO> wrapper() {
		LambdaQueryWrapper<SystemFilePO> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(SystemFilePO::getDeleted, 0);
		return wrapper;
	}

}
