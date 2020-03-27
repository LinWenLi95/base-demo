package ${codeConfig.servicePack};

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * 通用service，表对象的service接口继承此接口
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
     * 获取对象信息
     * @param id 用户id
     * @return T
     */
    default T selectById(Integer id) {
        return getMapper().selectById(id);
    }

    /**
     * 获取对象列表
     * @param paramMap 查询条件
     * @return List<T>
     */
    default List<T> selectByMap(Map<String, Object> paramMap) {
        return getMapper().selectByMap(paramMap);
    }

    /**
     * 添加对象信息
     * @param t 要添加的对象数据
     * @return Integer 1成功，0失败
     */
    default Integer add(T t) {
        return getMapper().insert(t);
    }

    /**
     * 更新对象信息
     * @param t 要更新的对象信息
     * @return Integer 1成功，0失败
     */
    default Integer update(T t) {
        return getMapper().updateById(t);
    }

    /**
     * 删除对象信息
     * @param id 对象id
     * @return Integer 1成功，0失败
     */
    default Integer delById(Integer id) {
        return getMapper().deleteById(id);
    }

}
