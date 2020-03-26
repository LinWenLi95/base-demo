package com.lwl.base.code.generator.util;

import org.springframework.util.StringUtils;

public class StringUtil {

    /**
     * 表字段转驼峰
     *      例：user_id -> userId
     * @param name 字段名
     * @return str
     */
    public static String underscoreToCamelCase(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        String[] arr = name.split("_");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            if (i == 0) {
                builder.append(arr[i]);
            } else {
                builder.append(StringUtils.capitalize(arr[i]));
            }
        }
        return builder.toString();
    }
}
