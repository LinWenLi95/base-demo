package com.lwl.base.spring.security.service.impl;

import com.lwl.base.common.base.BaseMapper;
import com.lwl.base.spring.security.dao.SysRoleMapper;
import com.lwl.base.spring.security.entity.pojo.SysRole;
import com.lwl.base.spring.security.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* 系统 角色表Service类
* @author LinWenLi
* @date 2020/04/09
*/
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired(required = false)
    private SysRoleMapper<SysRole> sysRoleMapper;

    @Override
    public BaseMapper<SysRole> getMapper(){
        return sysRoleMapper;
    }

    @Override
    public Integer remove(Integer id) {
        SysRole sysRole = new SysRole();
        sysRole.setId(id);
        // 更新逻辑删除字段状态
        sysRole.setIsDel(true);
        return sysRoleMapper.update(sysRole);
    }

    @Override
    public List<SysRole> queryListByUserId(Integer userId) {
        return sysRoleMapper.queryListByUserId(userId);
    }

    @Override
    public List<SysRole> queryListByUrl(String url) {
        return sysRoleMapper.queryListByUrl(url);
    }
}