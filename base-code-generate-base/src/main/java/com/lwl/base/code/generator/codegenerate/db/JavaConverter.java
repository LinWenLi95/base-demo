package com.lwl.base.code.generator.codegenerate.db;

/**
 * 转换器 数据库表信息转Java类信息
 * @author Admin
 */
public interface JavaConverter<T> {
    /**
     * 转换方法 数据库表/列信息转Java类信息
     * @return T
     */
    T convert();
}