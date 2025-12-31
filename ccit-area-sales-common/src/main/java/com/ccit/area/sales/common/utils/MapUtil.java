package com.ccit.area.sales.common.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 描述 : Map工具类
 * 创建人 : yn
 * 创建时间 : 2024年7月8日 下午5:02:14
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.common.utils
 * 类名 : MapUtil
 */
public class MapUtil {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月8日 下午5:03:12
	 * 描述 : 将Object对象里面的属性和值转化成Map对象
	 * 包名 : com.ccit.area.sales.common.utils
	 * 方法名 : objectToMap
	 *  Map<String,Object>  
	 *  @throws
	 */
    public static Map<String, Object> objectToMap(Object obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            try {
				map.put(field.getName(), field.get(obj));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
        }
        return map;
    }
    
    /**
     * 
     * 创建人 : yn
     * 创建时间 : 2024年7月8日 下午5:03:48
     * 描述 : 将大写字段全部改为小写并增加下划线
     * 包名 : com.ccit.area.sales.common.utils
     * 方法名 : upperCharToUnderLine
     *  String
     *  @throws
     */
	public static String upperCharToUnderLine(String param) {
		Pattern p = Pattern.compile("[A-Z]");
		if (param == null || param.equals("")) {
			return "";
		}
		StringBuilder builder = new StringBuilder(param);
		Matcher mc = p.matcher(param);
		int i = 0;
		while (mc.find()) {
			builder.replace(mc.start() + i, mc.end() + i, "_" + mc.group().toLowerCase());
			i++;
		}
		if ('_' == builder.charAt(0)) {
			builder.deleteCharAt(0);
		}
		return builder.toString();
	}
	
}
