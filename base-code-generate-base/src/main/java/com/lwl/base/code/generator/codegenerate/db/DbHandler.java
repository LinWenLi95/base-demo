package com.lwl.base.code.generator.codegenerate.db;

import com.lwl.base.code.generator.codegenerate.model.EntityBean;

import java.util.List;

/**
 * 数据库处理
 * @author Admin
 */
public interface DbHandler {

    /**
     * 获取表信息对象列表（所有表）
     * @return 表信息对象列表
     */
    List<EntityBean> allTables();

    /**
     * 获取表信息对象（指定表名的单表）
     * @param tableName 表名
     * @return 表信息对象
     */
    EntityBean byTableName(String tableName);

    /**
     * 关闭数据库连接相关对象
     */
    void close();
}
