package com.lwl.base.common.util;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestUtil {

    /**
     * 返回request中的参数集合
     * @param request 请求对象
     * @return Map<String, Object>
     */
    public static Map<String, Object> getParameterMap(HttpServletRequest request) {
        Map<String, String[]> parameterMap;
        if (request != null && (parameterMap = request.getParameterMap()) != null) {
            Map<String, Object> param = new HashMap<>(parameterMap.size());
            for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
                System.out.println(entry.getKey());
                System.out.println(entry.getValue()[0]);
                param.put(entry.getKey(), entry.getValue()[0]);
                System.out.println("-------------------------");
            }
            return param;
        }
        return null;
    }
}
