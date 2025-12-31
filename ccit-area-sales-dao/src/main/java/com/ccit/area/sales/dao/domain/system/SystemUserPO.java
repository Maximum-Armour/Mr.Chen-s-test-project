package com.ccit.area.sales.dao.domain.system;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ccit.area.sales.dao.domain.BasePO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 
 * 描述 : “用户”实体类
 * 创建人 : yn
 * 创建时间 : 2024年7月4日 上午10:01:37
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.domain.system
 * 类名 : SystemUserPO
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName(value = "t_system_user")
public class SystemUserPO extends BasePO<SystemUserPO> {

	private static final long serialVersionUID = 1L;

	/**
     * 用户账号
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 用户姓名
     */
    @TableField(value = "real_name")
    private String realName;

    /**
     * 用户头像
     */
    @TableField(value = "user_photo")
    private String userPhoto;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
	@TableField(updateStrategy = FieldStrategy.IGNORED)
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 个性签名
     */
	@TableField(updateStrategy = FieldStrategy.IGNORED)
    private String signature;

    /**
     * 状态：0-正常；1-禁用；2-锁定；
     */
    private Integer status;

    /**
     * 锁定时间
     */
    @TableField(value = "locked_date", updateStrategy = FieldStrategy.IGNORED)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lockedDate;

    /**
     * 登录失败次数
     */
    @TableField(value = "login_fail_count")
    private Integer loginFailCount;

    /**
     * 密码是否已过期：0-否；1-是；
     */
    @TableField(value = "password_expired")
    private Integer passwordExpired;

    /**
     * 最后更新密码时间
     */
    @TableField(value = "password_last_changed")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date passwordLastChanged;

	/**
	 * 钉钉UserId
	 */
	@TableField("dingding_user_id")
	private String dingdingUserId;

    /**
     * 
     * 创建人 : yn
     * 创建时间 : 2024年7月4日 上午10:19:17
     * 构造方法名 : SystemUserPO()
     * 描述 : 
     */
	public SystemUserPO() {
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月1日 上午9:59:17
	 * 构造方法名 : SystemUserPO(Boolean deleted, Boolean isAutoFillUser)
	 * 描述 : 处理逻辑删除
	 * 参数 :	deleted			删除标识：true-已删除；false-未删除；
     *		isAutoFillUser	是否自动填充用户：true-是；false-否；
	 */
	public SystemUserPO(Boolean deleted, Boolean isAutoFillUser) {
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
	 * 创建时间 : 2024年7月4日 上午10:22:08
	 * 描述 : 包装器
	 * 包名 : com.ccit.area.sales.dao.domain.system
	 * 方法名 : wrapper
	 *  LambdaQueryWrapper<SystemUserPO>  
	 *  @throws
	 */
	public static LambdaQueryWrapper<SystemUserPO> wrapper() {
		LambdaQueryWrapper<SystemUserPO> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(SystemUserPO::getDeleted, 0);
		return wrapper;
	}
    
}
