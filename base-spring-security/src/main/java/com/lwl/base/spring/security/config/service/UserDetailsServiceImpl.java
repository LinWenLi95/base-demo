//package com.lwl.base.spring.security.config.service;
//import com.lwl.base.spring.security.entity.pojo.SysPermission;
//import com.lwl.base.spring.security.entity.pojo.SysUser;
//import com.lwl.base.spring.security.service.SysPermissionService;
//import com.lwl.base.spring.security.service.SysUserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 自定义用户认证与授权
// * <p>
// * Description:
// * </p>
// *
// * @author Lusifer
// * @version v1.0.0
// * @date 2019-04-04 23:57:04
// */
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    @Autowired
//    private SysUserService sysUserService;
//
//    @Autowired
//    private SysPermissionService sysPermissionService;
//
//    @Override
//    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//        // 查询用户信息
//        SysUser sysUser = sysUserService.queryByName(userName);
//        if (sysUser != null) {
//            //生产环境是查询数据库获取username的角色用于后续权限判断（如：张三 admin)
//            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//            // 获取用户授权
//            List<SysPermission> sysPermissions = sysPermissionService.queryByUserId(sysUser.getId());
//            // 声明用户授权
//            sysPermissions.forEach(sysPermission -> {
//                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(sysPermission.getUrl());
//                grantedAuthorities.add(grantedAuthority);
//            });
//            //创建一个用户，用于判断权限，请注意此用户名和方法参数中的username一致；BCryptPasswordEncoder是用来演示加密使用。
//            // 由框架完成认证工作
//            return new User(sysUser.getUsername(), sysUser.getPassword(), grantedAuthorities);
//        }
//        throw new RuntimeException("账号或密码错误");
//    }
//}
