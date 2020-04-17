package com.lwl.base.spring.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private MyAccessDecisionManager myAccessDecisionManager;
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthentication;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
                // 禁用 csrf, 使用的是JWT，不需要csrf
        http
                .csrf().disable()
                .cors()
                .and().authenticationProvider()
                .authorizeRequests()
                // 跨域预检请求
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 登录URL
                .antMatchers(SecurityConstants.AUTH_LOGIN_URL).permitAll()
                .antMatchers("/login").permitAll()
                // 其他所有请求需要身份认证
                .anyRequest().authenticated()
                .accessDecisionManager(myAccessDecisionManager)
                .and()
                .authenticationProvider(jwtAuthentication)
                .httpBasic()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 关闭form表单登录
                .formLogin().disable();


//                .and()
//                .logout().logoutUrl(SecurityConstants.AUTH_LOGOUT_URL);
    }
}
