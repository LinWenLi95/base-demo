package com.lwl.base.spring.security.dao;

import com.lwl.base.common.base.BaseMapper;
import com.lwl.base.spring.security.entity.pojo.SysPermission;

import java.util.List;

/**
* 系统 权限表数据库操作接口类
* @author LinWenLi
* @date 2020/04/07
*/
public interface SysPermissionMapper<T extends SysPermission> extends BaseMapper<T> {

    /**
     * 获取用户权限信息列表
     * @param userId 用户id
     * @return List<SysPermission>
     */
    List<SysPermission> queryByUserId(Integer userId);
}