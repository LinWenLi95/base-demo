package com.lwl.base.spring.security.config;

import com.lwl.base.spring.security.entity.pojo.SysPermission;
import com.lwl.base.spring.security.entity.pojo.SysRole;
import com.lwl.base.spring.security.service.SysPermissionService;
import com.lwl.base.spring.security.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * URL权限管理拦截器 元数据
 * 为权限决断器提供数据支持 （获取有权限访问指定URL的角色）
 * @author LinWenLi
 */
@Slf4j
@Component
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private SysPermissionService sysPermissionService;
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 获取访问该url所需要的1权限信息
     * 判定用户请求的url是否在权限表中
     * 如果在权限表中，则返回给decide方法，用来判定用户是否有此权限
     * 如果不在权限表中则放行
     *
     * @param object
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        //Object中包含用户请求request
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        String method = ((FilterInvocation) object).getRequest().getMethod();
        Integer methodType = 1;
        System.out.println(method);
        /*获取所有权限 已启用 未删除的权限*/
        // 后续改成缓存中获取权限列表
        List<SysPermission> permissions = sysPermissionService.queryList(null, null);
        for (SysPermission permission : permissions) {
            // 若权限列表中能匹配请求url
            if (requestUrl.equals(permission.getUrl()) && methodType.equals(permission.getType())) {
                // 获取能访问该url的所有角色并返回
                List<SysRole> roles = sysRoleService.queryListByUrl(requestUrl);
                if (roles != null && roles.size() > 0) {
                    // 保存该url对应角色权限信息 作为MyAccessDecisionManager类的decide的第三个参数
                    return SecurityConfig.createList(roles.stream().map(SysRole::getEnname).toArray(String[]::new));
                }
            }
        }
        return SecurityConfig.createList("没有访问权限");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
