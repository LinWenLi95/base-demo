package com.lwl.base.code.gen.service.impl;

import com.lwl.base.code.common.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lwl.base.code.gen.entity.pojo.SysUser;
import com.lwl.base.code.gen.dao.SysUserMapper;
import com.lwl.base.code.gen.service.SysUserService;


/**
* 系统 用户表Service类
* @author LinWenLi
* @date 2020/04/05
*/
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired(required = false)
    private SysUserMapper<SysUser> sysUserMapper;

    @Override
    public BaseMapper<SysUser> getMapper(){
        return sysUserMapper;
    }

    @Override
    public Integer remove(Integer id) {
        SysUser sysUser = new SysUser();
        sysUser.setId(id);
        sysUser.setIsDel(true);
        return sysUserMapper.update(sysUser);
    }
}