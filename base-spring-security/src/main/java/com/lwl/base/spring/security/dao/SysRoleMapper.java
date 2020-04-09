package com.lwl.base.spring.security.dao;

import com.lwl.base.common.base.BaseMapper;
import com.lwl.base.spring.security.entity.pojo.SysRole;

import java.util.List;

/**
* 系统 角色表数据库操作接口类
* @author LinWenLi
* @date 2020/04/09
*/
public interface SysRoleMapper<T extends SysRole> extends BaseMapper<T> {

    /**
     * 查询用户匹配的角色列表
     * @param userId 用户id
     * @return List<SysRole>
     */
    List<SysRole> queryListByUserId(Integer userId);

    /**
     * 获取有访问权限的角色（后续应改为从缓存中获取）
     * @param url 请求url
     * @return List<SysRole>
     */
    List<SysRole> queryListByUrl(String url);
}