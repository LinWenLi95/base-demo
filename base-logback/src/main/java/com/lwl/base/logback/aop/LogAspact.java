package com.lwl.base.logback.aop;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 日志切面处理
 * @date 2019-11-28
 * @author lwl
 */
@Slf4j
@Aspect
@Component
public class LogAspact {

    /**
     * 切入点设置为@ApiLog注解
     */
    @Pointcut(value = "@annotation(com.lwl.base.logback.annotation.ApiLog)")
    public void logPointCut() {
    }

    /**
     * 为切入点设置环绕通知
     */
    @Around("logPointCut()")
    public Object logHandler(ProceedingJoinPoint process) {
        long startTime = System.currentTimeMillis();
        long costTime;
        // 获取类名、方法名
        Signature signature = process.getSignature();
        String className = signature.getDeclaringTypeName();
        String methodName = signature.getName();
        // 获取请求对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        Set<Map.Entry<String, String[]>> entries = request.getParameterMap().entrySet();
        Map<String, Object> mapParams = new HashMap<>();
        StringBuilder params = new StringBuilder();
        for (Map.Entry<String, String[]> entry : entries) {
            mapParams.put(entry.getKey(), entry.getValue()[0]);
            params.append(entry.getKey()).append("=").append(entry.getValue()[0]).append("&");
        }
        if (params.length() > 0) {
            params.deleteCharAt(params.length() - 1);
        }
        // 获取请求结果
        Object result = null;
        try {
            // 继续执行目标方法，并获取返回值
            result = process.proceed();
        } catch (Throwable e) {
            costTime = System.currentTimeMillis() - startTime;
            log.error("***************");
            log.error("请求URI：{}",request.getRequestURI());
            log.error("请求方式：{}",request.getMethod());
            log.error("请求参数：{}", JSON.toJSONString(mapParams));
            log.error("请求URL：{}?{}", request.getRequestURL(), params.toString());
            log.error("客户端IP：{}，User-Agent：{}", request.getRemoteAddr(),request.getHeader("User-Agent"));
            log.error("请求耗时：{} ms，请求时间：{}", costTime, DateFormatUtils.format(startTime, "yyyy-MM-dd HH:mm:ss.SSS"));
            log.error("请求类名：{}，请求方法：{}", className, methodName);
            log.error("错误信息：{}:{}", e.getClass(), e.getMessage());
            log.error("***************");
            return null;
        }
        costTime = System.currentTimeMillis() - startTime;

        log.info("***************");
        log.info("请求URI：{}",request.getRequestURI());
        log.info("请求方式：{}",request.getMethod());
        log.info("请求参数：{}", JSON.toJSONString(mapParams));
        log.info("请求URL：{}?{}", request.getRequestURL(), params.toString());
        log.info("客户端IP：{}，User-Agent：{}", request.getRemoteAddr(),request.getHeader("User-Agent"));
        log.info("请求耗时：{} ms，请求时间：{}", costTime, DateFormatUtils.format(startTime, "yyyy-MM-dd HH:mm:ss.SSS"));
        log.info("请求类名：{}，请求方法：{}", className, methodName);
        log.info("请求结果：{}", result);
        log.info("***************");

        return result;
    }

    @Before(value = "logPointCut()")
    public void before() {}

    @After(value = "logPointCut()")
    public void after() {}

}
