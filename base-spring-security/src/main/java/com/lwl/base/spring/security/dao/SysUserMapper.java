package com.lwl.base.spring.security.dao;

import com.lwl.base.common.base.BaseMapper;
import com.lwl.base.spring.security.entity.pojo.SysUser;

/**
* 系统 用户表数据库操作接口类
* @author LinWenLi
* @date 2020/04/05
*/
public interface SysUserMapper<T extends SysUser> extends BaseMapper<T> {

    /**
     * 通过用户名查询实体
     * @param userName 用户名
     * @return SysUser
     */
    SysUser selectByName(String userName);
}