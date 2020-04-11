package com.lwl.code.generator.util;

import org.springframework.util.StringUtils;

public class StringUtil {

    /**
     * 下划线转驼峰
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

    /**
     * 驼峰转下划线
     *      例：userId -> user_id
     * @param name 字段名
     * @return str
     */
    public static String camelCaseToUnderscore(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        int len = name.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = name.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append("_");
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }

}
