package com.lwl.base.spring.security.config;
import com.lwl.base.spring.security.entity.pojo.SysRole;
import com.lwl.base.spring.security.entity.pojo.SysUser;
import com.lwl.base.spring.security.service.SysRoleService;
import com.lwl.base.spring.security.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义用户认证与授权
 * @author LinWenLi
 * @date 2020-04-04 23:57:04
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        // 查询用户信息
        SysUser sysUser = sysUserService.queryByName(userName);
        if (sysUser != null) {
            // 获取用户的角色列表
            List<SysRole> roles = sysRoleService.queryListByUserId(sysUser.getId());
            List<GrantedAuthority> grantedAuthorities = roles.stream()
                    .map(role -> new SimpleGrantedAuthority(role.getEnname()))
                    .collect(Collectors.toList());
            //创建一个用户对象，存入用户名/密码/用户的角色 用于认证授权
            return new User(sysUser.getUsername(), sysUser.getPassword(), grantedAuthorities);
        }
        throw new RuntimeException("账号或密码错误");
    }
}
