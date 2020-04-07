package com.lwl.base.common.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 通用mapper，数据表映射的mapper接口继承此接口
 * @author Admin
 * @param <T>
 */
public interface BaseMapper<T> {

    /**
     * 查询单条记录（主键ID查询）
     * @param id 主键ID
     * @param columns 要返回数据的列名
     * @return T
     */
    T selectById(Serializable id, List<String> columns);

    /**
     * 查询单条记录（map条件查询）
     * @param columnMap 属性条件集合
     * param T的属性名（可选,所有属性都可作为条件）
     * param columns 要返回数据的列名
     * @return T
     */
    T selectByMap(Map<String, Object> columnMap);

    /**
     * 查询多条记录（分页map条件查询）
     * @param columnMap 查询条件集合<br/>
     * &nbsp;&nbsp;&nbsp;&nbsp; T对象的属性名（可选,所有属性都可作为条件）<br/>
     * &nbsp;&nbsp;&nbsp;&nbsp; offset 跳过行数（可选，必须与limit配合使用）<br/>
     * &nbsp;&nbsp;&nbsp;&nbsp; limit 取出数量（可选，可单独使用）<br/>
     * &nbsp;&nbsp;&nbsp;&nbsp; order_by 排序字段（可选，必须与sort配合使用）<br/>
     * &nbsp;&nbsp;&nbsp;&nbsp; sort 排序方式（可选，值选填：asc|desc，必须与order_by配合使用）
     * @return List<T>
     */
    List<T> selectPage(Map<String, Object> columnMap);

    /**
     * 统计数量（map条件查询）
     * @param columnMap 属性条件集合
     * param T的属性名（可选,所有属性都可作为条件）
     * @return 数量
     */
    int count(Map<String, Object> columnMap);

    /**
     * 添加 单条记录
     * @param t 实体对象
     * @return id
     */
    int insert(T t);

    /**
     * 更新 单条记录
     * @param t 实体对象
     * @return 影响条数
     */
    int update(T t);

    /**
     * 删除 单条记录（此方法为物理删除，逻辑删除请使用update方法）
     * @param id 主键
     * @return 影响条数
     */
    int delete(Serializable id);
}
