package com.lwl.code.generator.db;

import com.lwl.code.generator.model.EntityBean;

import java.util.List;

/**
 * 数据库处理
 * @author Admin
 */
public interface DbHandler {

    /**
     * 获取表信息对象列表
     * @param tableName 要生成代码的表名
     * @return 表信息对象列表
     */
    List<EntityBean> tableListByName(String... tableName);

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
