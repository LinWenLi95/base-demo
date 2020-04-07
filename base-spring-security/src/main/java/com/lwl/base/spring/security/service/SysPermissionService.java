package com.lwl.base.spring.security.service;

import com.lwl.base.common.base.BaseService;
import com.lwl.base.spring.security.entity.pojo.SysPermission;

import java.util.List;

/**
* 系统 权限表Service接口
* @author LinWenLi
* @date 2020/04/07
*/
public interface SysPermissionService extends BaseService<SysPermission> {

    /**
     * 获取用户权限信息列表
     * @param userId 用户id
     * @return List<SysPermission>
     */
    List<SysPermission> queryByUserId(Integer userId);
}