package com.lwl.base.spring.security.service;

import com.lwl.base.common.base.BaseService;
import com.lwl.base.spring.security.entity.pojo.SysRole;

import java.util.List;

/**
* 系统 角色表Service接口
* @author LinWenLi
* @date 2020/04/09
*/
public interface SysRoleService extends BaseService<SysRole> {

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