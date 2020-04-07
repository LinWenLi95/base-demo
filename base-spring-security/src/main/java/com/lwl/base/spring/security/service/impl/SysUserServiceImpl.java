package com.lwl.base.spring.security.service.impl;

import com.lwl.base.common.base.BaseMapper;
import com.lwl.base.spring.security.dao.SysUserMapper;
import com.lwl.base.spring.security.entity.pojo.SysUser;
import com.lwl.base.spring.security.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


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

    @Override
    public SysUser queryByName(String userName) {
        if (!StringUtils.isEmpty(userName)) {
            return sysUserMapper.selectByName(userName);
        }
        return null;
    }
}