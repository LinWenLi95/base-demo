package com.lwl.base.spring.security.config;

import com.lwl.base.spring.security.entity.pojo.SysPermission;
import com.lwl.base.spring.security.entity.pojo.SysRole;
import com.lwl.base.spring.security.service.SysPermissionService;
import com.lwl.base.spring.security.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class MyAccessDecisionManager implements AccessDecisionManager {

    @Autowired
    private SysPermissionService sysPermissionService;
    @Autowired
    private SysRoleService sysRoleService;

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        //Object中包含用户请求request
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        String method = ((FilterInvocation) object).getRequest().getMethod();
        Integer methodType = 1;
        System.out.println(method);
        String[] roleNames = getRoleNames(requestUrl, methodType);
        if (roleNames != null) {
            // 遍历可访问url的角色与用户角色
            for (String needRole : roleNames) {
                for (GrantedAuthority ga : authentication.getAuthorities()) {
                    // 只要包含其中一个角色即可访问
                    if (needRole.trim().equals(ga.getAuthority())) {
                        return;
                    }
                }
            }
        }
        throw new AccessDeniedException("抱歉，您没有访问权限");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    /**
     * 获取有该url访问权限的所有角色名 TODO 需要改成缓存中获取权限列表
     */
    private String[] getRoleNames(String requestUrl, Integer methodType) {
        /*获取所有权限 已启用 未删除的权限*/
        List<SysPermission> permissions = sysPermissionService.queryList(null, null);
        for (SysPermission permission : permissions) {
            // 若权限列表中能匹配请求url
            if (requestUrl.equals(permission.getUrl()) && methodType.equals(permission.getType())) {
                // 获取能访问该url的所有角色并返回
                List<SysRole> roles = sysRoleService.queryListByUrl(requestUrl);
                if (roles != null && roles.size() > 0) {
                    return roles.stream().map(SysRole::getEnname).toArray(String[]::new);
                }
            }
        }
        return null;
    }
}
