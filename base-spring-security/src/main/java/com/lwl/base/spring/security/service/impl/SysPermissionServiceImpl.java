package com.lwl.base.spring.security.service.impl;

import com.lwl.base.common.base.BaseMapper;
import com.lwl.base.spring.security.dao.SysPermissionMapper;
import com.lwl.base.spring.security.entity.pojo.SysPermission;
import com.lwl.base.spring.security.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* 系统 权限表Service类
* @author LinWenLi
* @date 2020/04/07
*/
@Service
public class SysPermissionServiceImpl implements SysPermissionService {

    @Autowired(required = false)
    private SysPermissionMapper<SysPermission> sysPermissionMapper;

    @Override
    public BaseMapper<SysPermission> getMapper(){
        return sysPermissionMapper;
    }

    @Override
    public Integer remove(Integer id) {
        SysPermission sysPermission = new SysPermission();
        sysPermission.setId(id);
        // 更新逻辑删除字段状态
        sysPermission.setIsDel(true);
        return sysPermissionMapper.update(sysPermission);
    }

    @Override
    public List<SysPermission> queryByUserId(Integer userId) {
        if (userId != null) {
            return sysPermissionMapper.queryByUserId(userId);
        }
        return null;
    }
}