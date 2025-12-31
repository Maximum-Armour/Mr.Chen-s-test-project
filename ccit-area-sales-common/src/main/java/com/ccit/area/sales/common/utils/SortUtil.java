package com.ccit.area.sales.common.utils;

import com.ccit.area.sales.common.exception.BusinessException;

import java.util.Objects;

/**
 * 列表升降排序工具类
 */
public class SortUtil {

    /**
     * 将传入的驼峰命名格式的字段名转换为下划线命名格式，并验证排序方向。
     *
     * @param field 格式为 "fieldName-sortDirection" 的字符串
     * @return 包含两个元素的数组：[0] 为转换后的字段名，[1] 为排序方向（小写）
     */
    public static String convertCamelToUnderline(Object field) {
        if (Objects.isNull(field)) {
            throw new BusinessException("参数为空");
        }

        // 转换字段名为下划线命名格式
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < field.toString().length(); i++) {
            char c = field.toString().charAt(i);
            if (Character.isUpperCase(c)) {
                if (i > 0) {
                    sb.append('_');
                }
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

}
