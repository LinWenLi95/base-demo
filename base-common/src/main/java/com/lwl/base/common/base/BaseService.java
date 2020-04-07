package com.lwl.base.common.base;

import com.lwl.base.common.util.StringUtil;
import com.lwl.base.common.vo.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用service，数据表映射的service接口继承此接口
 * @author lwl
 * @param <T>
 */
public interface BaseService<T> {

    /**
     * 获取基本mapper
     * @return BaseMapper
     */
    BaseMapper<T> getMapper();

    /**
     * 查询单条记录（主键ID查询）
     * @param id 主键id
     * @param columns 要返回数据的列名（放空则返回全部列）
     * @return T
     */
    default T queryById(Integer id, List<String> columns) {
        return getMapper().selectById(id, columns);
    }

    /**
     * 查询单条记录（map条件查询）
     * @param columnMap 属性条件集合
     * @param columns   要返回数据的列名
     * @return T
     */
    default T queryByMap(Map<String, Object> columnMap, List<String> columns) {
        if (columns != null) {
            if (columnMap == null) {
                columnMap = new HashMap<>(1);
            }
            columnMap.put("columns", columns);
        }
        return getMapper().selectByMap(columnMap);
    }

    /**
     * 查询多条记录（map条件查询）
     * @param columnMap 查询条件 T对象的属性名（所有属性都可作为条件）
     * @param columns 要返回数据的列名（放空则返回全部列）
     * @return List<T>
     */
    default List<T> queryList(Map<String, Object> columnMap, List<String> columns) {
        if (columns != null) {
            if (columnMap == null) {
                columnMap = new HashMap<>(1);
            }
            columnMap.put("columns", columns);
        }
        return getMapper().selectPage(columnMap);
    }

    /**
     * 查询多条记录（分页map条件查询）
     * @param paramMap 查询条件集合<br/>
     * &nbsp;&nbsp;&nbsp;&nbsp; T对象的属性名（可选,所有属性都可作为条件）<br/>
     * &nbsp;&nbsp;&nbsp;&nbsp; current 当前页码（可选，必须与limit配合使用）<br/>
     * &nbsp;&nbsp;&nbsp;&nbsp; limit 取出数量（可选，可单独使用）<br/>
     * &nbsp;&nbsp;&nbsp;&nbsp; order_by 排序字段（可选，必须与sort配合使用）<br/>
     * &nbsp;&nbsp;&nbsp;&nbsp; sort 排序方式（可选，值选填：asc|desc，必须与order_by配合使用）
     * @param columns 要返回数据的列名（放空则返回全部列）
     * @return Page<T>
     */
    default Page<T> queryPage(Map<String, Object> paramMap, List<String> columns) {
        int count = getMapper().count(paramMap);
        int limit = count;
        int current = 1;
        if (paramMap != null) {
            // 通过分页参数current、limit计算offset
            Object currentObj = paramMap.get("current");
            Object limitObj = paramMap.get("limit");
            if (currentObj != null && limitObj != null) {
                current = Integer.parseInt(String.valueOf(currentObj));
                // 当前页最小为1
                current = current <= 0 ? 1 : current;
                limit = Integer.parseInt(String.valueOf(limitObj));
                // 计算跳过行数
                Integer offset = (current - 1) * limit;
                paramMap.put("offset", offset);
            }
            // 规范排序参数值，将驼峰转为下划线
            String orderBy = String.valueOf(paramMap.get("order_by"));
            String sort = String.valueOf(paramMap.get("sort"));
            if (!StringUtils.isEmpty(orderBy) && !StringUtils.isEmpty(sort)) {
                // 若sort值正确则将order_by字段的值由驼峰转下划线，否则移除排序参数
                String lowerCase = sort.toLowerCase();
                if ("asc".equals(lowerCase) || "desc".equals(lowerCase)) {
                    paramMap.put("order_by", StringUtil.camelCaseToUnderscore(orderBy));
                }else {
                    paramMap.remove("order_by");
                    paramMap.remove("sort");
                }
            }
        }else {
            if (columns != null) {
                paramMap = new HashMap<>(1);
                paramMap.put("columns", columns);
            }
        }
        List<T> records = getMapper().selectPage(paramMap);
        Page<T> page = new Page<>(current, limit, count);
        page.setRecords(records);
        return page;
    }

    /**
     * 统计数量（map条件查询）
     * @param columnMap 属性条件集合
     * @return 数量
     */
    default int count(Map<String, Object> columnMap){
        return getMapper().count(columnMap);
    }

    /**
     * 添加 单条记录
     * @param t 要添加的对象数据
     * @return id
     */
    default Integer add(T t) {
        return getMapper().insert(t);
    }

    /**
     * 更新 单条记录
     * @param t 要更新的对象信息
     * @return 影响条数
     */
    default Integer edit(T t) {
        return getMapper().update(t);
    }

    /**
     * 删除 单条记录（建议重写此方法为逻辑删除）
     * @param id 对象id
     * @return 影响条数
     */
    default Integer remove(Integer id) {
        return getMapper().delete(id);
    }

    /**
     * 对象转换（复制匹配数据）
     * @param source 源数据对象
     * @param targetClass 目标类类型
     * @param <G> 目标类泛型
     * @return 目标类对象
     */
    default <G> G convert(T source, Class<G> targetClass) {
        G target = null;
        try {
            target = targetClass.newInstance();
            BeanUtils.copyProperties(source, target);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return target;
    }

}
