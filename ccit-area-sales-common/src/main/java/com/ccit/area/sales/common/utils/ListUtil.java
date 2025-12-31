package com.ccit.area.sales.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;

/**
 * 
 * 描述 : 数组工具类
 * 创建人 : yn
 * 创建时间 : 2024年7月9日 上午9:26:58
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.common.utils
 * 类名 : ListUtil
 */
public class ListUtil {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 上午9:27:38
	 * 描述 : 集合中的元素转化程指定class类
	 * 包名 : com.ccit.area.sales.common.utils
	 * 方法名 : arrayCopyTo
	 *  List  
	 *  @throws
	 */
	@SuppressWarnings({ "all" })
	public static List arrayCopyTo(List fromList, Class toClass) {
		if (CollectionUtils.isEmpty(fromList)) {
			return null;
		}
		try {
			List<Object> toList = new ArrayList<>();
			Object tempObj;
			for (Object aFromList : fromList) {
				tempObj = toClass.newInstance();
				BeanUtils.copyProperties(aFromList, tempObj, toClass);
				toList.add(tempObj);
			}
			return toList;
		} catch (Exception e) {
			return fromList;
		}
	}
	
}
