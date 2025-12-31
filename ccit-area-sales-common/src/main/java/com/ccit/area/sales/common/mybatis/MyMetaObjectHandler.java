package com.ccit.area.sales.common.mybatis;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.function.Supplier;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.ccit.area.sales.common.utils.CurrentUserUtil;

/**
 * 
 * 描述 : 字段自动补充处理
 * 创建人 : yn
 * 创建时间 : 2024年7月10日 下午3:01:38
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.common.mybatis
 * 类名 : MyMetaObjectHandler
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月10日 上午10:58:24
	 * 描述 : 新增时自动填充
	 * 包名 : com.ccit.area.sales.common.mybatis
	 * 方法名 : insertFill
	 *  void  
	 *  @throws
	 */
	@Override
	public void insertFill(MetaObject metaObject) {
		JSONObject currentUser = CurrentUserUtil.getCurrentUser();
		if (currentUser != null && !currentUser.isEmpty()) {
			this.strictInsertFill(metaObject, "createBy", String.class, String.valueOf(currentUser.getLong("userId")));
			this.strictInsertFill(metaObject, "createByName", String.class, currentUser.getString("realName"));
		}
		Arrays.stream(metaObject.getSetterNames()).forEach(propName -> {
            if (metaObject.getGetterType(propName) == String.class) {
                String value = (String) metaObject.getValue(propName);
                if (value!= null && value.trim().isEmpty()) {
                    metaObject.setValue(propName, null);
                }
            }
        });
		this.strictInsertFill(metaObject, "gmtCreate", Date.class, new Date());
		this.strictInsertFill(metaObject, "gmtModified", Date.class, new Date());
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月10日 上午10:58:31
	 * 描述 : 更新时自动填充
	 * 包名 : com.ccit.area.sales.common.mybatis
	 * 方法名 : updateFill
	 *  void  
	 *  @throws
	 */
	@Override
	public void updateFill(MetaObject metaObject) {
		JSONObject currentUser = CurrentUserUtil.getCurrentUser();
		if (currentUser != null && !currentUser.isEmpty()) {
			this.strictInsertFill(metaObject, "updateBy", String.class, String.valueOf(currentUser.getLong("userId")));
			this.strictInsertFill(metaObject, "updateByName", String.class, currentUser.getString("realName"));
		}
		Arrays.stream(metaObject.getSetterNames()).forEach(propName -> {
            if (metaObject.getGetterType(propName) == String.class) {
                String value = (String) metaObject.getValue(propName);
                if (value!= null && value.trim().isEmpty()) {
                    metaObject.setValue(propName, null);
                }
            }
        });
		this.strictUpdateFill(metaObject, "gmtModified", Date.class, new Date());
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月10日 上午10:58:39
	 * 描述 : 解决【更新时自动填充】原字段有值无法更新问题
	 * 包名 : com.ccit.area.sales.common.mybatis
	 * 方法名 : strictFillStrategy
	 *  MetaObjectHandler  
	 *  @throws
	 */
	@Override
	public MetaObjectHandler strictFillStrategy(MetaObject metaObject, String fieldName, Supplier<?> fieldVal) {
		{
			Object obj = fieldVal.get();
			if (Objects.nonNull(obj)) {
				metaObject.setValue(fieldName, obj);
			}
		}
		return this;
	}
	
}
