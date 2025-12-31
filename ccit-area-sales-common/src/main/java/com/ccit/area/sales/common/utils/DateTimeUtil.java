package com.ccit.area.sales.common.utils;

import java.util.Date;

/**
 * 
 * 描述 : 日期时间工具类
 * 创建人 : yn
 * 创建时间 : 2024年7月8日 下午3:33:11
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.common.utils
 * 类名 : DateTimeUtil
 */
public class DateTimeUtil {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月8日 下午3:33:52
	 * 描述 : 获取两个时间相减求差
	 * 包名 : com.ccit.area.sales.common.utils
	 * 方法名 : getTwoDateSubtract
	 *  String  
	 *  @throws
	 */
    public static String getTwoDateSubtract(Date oneDate, Date twoDate) {
		long diff = oneDate.getTime() - twoDate.getTime();
		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		long ns = 1000;
		// 计算差多少分钟
		Long min = diff % nd % nh / nm;
		// 计算差多少秒
		Long sec = diff % nd % nh % nm / ns;
		String takeTime = (String.format("%02d", min.intValue()) + ":" + String.format("%02d", sec.intValue()))
				.toString();
		return takeTime;
    }
	
}
