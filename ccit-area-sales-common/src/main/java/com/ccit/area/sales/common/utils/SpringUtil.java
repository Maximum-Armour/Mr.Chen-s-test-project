package com.ccit.area.sales.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 
 * 描述 : Spring工具类
 * 创建人 : yn
 * 创建时间 : 2024年7月10日 下午3:42:11
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.common.utils
 * 类名 : SpringUtil
 */
@Component
public class SpringUtil implements ApplicationContextAware {

	/**
	 * 应用上下文
	 */
	private static ApplicationContext applicationContext;

    /**
     * 
     * 创建人 : yn
     * 创建时间 : 2024年7月10日 下午3:42:18
     * 描述 : 获取应用上下文
     * 包名 : com.ccit.area.sales.common.utils
     * 方法名 : getApplicationContext
     *  ApplicationContext  
     *  @throws
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    
    /**
     * 
     * 创建人 : yn
     * 创建时间 : 2024年7月10日 下午3:42:28
     * 描述 : 赋值应用上下文
     * 包名 : com.ccit.area.sales.common.utils
     * 方法名 : setApplicationContext
     *  void  
     *  @throws
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringUtil.applicationContext == null) {
            SpringUtil.applicationContext = applicationContext;
        }
    }

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月10日 下午3:42:32
	 * 描述 : 拿到应用上下文对象实例后就可以手动获取Bean的注入实例对象
	 * 包名 : com.ccit.area.sales.common.utils
	 * 方法名 : getBean
	 *  T  
	 *  @throws
	 */
	public static <T> T getBean(Class<T> clazz) {
		return getApplicationContext().getBean(clazz);
	}

}
