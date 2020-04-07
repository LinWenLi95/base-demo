package com.lwl.base.spring.security.service;

import com.lwl.base.common.base.BaseService;
import com.lwl.base.spring.security.entity.pojo.SysUser;

/**
* 系统 用户表Service接口
* @author LinWenLi
* @date 2020/04/05
*/
public interface SysUserService extends BaseService<SysUser> {

    /**
     * 通过用户名查询实体（身份认证时使用）
     * @param userName 用户名
     * @return SysUser
     */
    SysUser queryByName(String userName);
}