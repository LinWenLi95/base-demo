package com.lwl.base.logback.annotation;

import java.lang.annotation.*;

/**
 * Api日志注解 注解在接口方法上，记录请求信息日志
 * @author lwl
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiLog {
}