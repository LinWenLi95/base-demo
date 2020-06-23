package com.lwl.base.spring.security.config;

import com.lwl.base.spring.security.config.service.JwtUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.header.Header;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * @author linwenli
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration1 extends WebSecurityConfigurerAdapter{

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //静态资源访问无需认证
                .antMatchers("/image/**").permitAll()
                //admin开头的请求，需要admin权限
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                //需登陆才能访问的url
                .antMatchers("/article/**").hasRole("USER")
                //默认其它的请求都需要认证，这里一定要添加
                .anyRequest().authenticated()
                .and()
                //CRSF禁用，因为不使用session
                .csrf().disable()
                //禁用session
                .sessionManagement().disable()
                //禁用form登录
                .formLogin().disable()
                //支持跨域
                .cors()
                .and()
                //添加header设置，支持跨域和ajax请求
                .headers().addHeaderWriter(new StaticHeadersWriter(Arrays.asList(
                new Header("Access-control-Allow-Origin","*"),
                new Header("Access-Control-Expose-Headers","Authorization"))))
                .and()
                //拦截OPTIONS请求，直接返回header
                .addFilterAfter(new OptionRequestFilter(), CorsFilter.class)
                //添加登录filter
                .apply(new JsonLoginConfigurer<>())
                .loginSuccessHandler(jsonLoginSuccessHandler())
                .and()
                //添加token的filter
                .apply(new JwtLoginConfigurer<>())
                .tokenValidSuccessHandler(jwtRefreshSuccessHandler())
                .permissiveRequestUrls("/logout")
                .and()
                //使用默认的logoutFilter
                .logout()
//              .logoutUrl("/logout")   //默认就是"/logout"
//                .addLogoutHandler(tokenClearLogoutHandler())  //logout时清除token
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler()) //logout成功后返回200
                .and()
                .sessionManagement().disable();
    }

    @Bean
    protected JwtRefreshSuccessHandler jwtRefreshSuccessHandler() {
        return new JwtRefreshSuccessHandler(jwtUserService());
    }

    @Bean
    protected JwtUserService jwtUserService() {
        return new JwtUserService();
    }

    @Bean
    protected JsonLoginSuccessHandler jsonLoginSuccessHandler() {
        return new JsonLoginSuccessHandler(jwtUserService());
    }



    //配置provider
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider()).authenticationProvider(jwtAuthenticationProvider());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean("jwtAuthenticationProvider")
    protected AuthenticationProvider jwtAuthenticationProvider() {
        return new JwtAuthenticationProvider(jwtUserService());
    }

    @Bean("daoAuthenticationProvider")
    protected AuthenticationProvider daoAuthenticationProvider() throws Exception{
        //这里会默认使用BCryptPasswordEncoder比对加密后的密码，注意要跟createUser时保持一致
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setUserDetailsService(userDetailsService());
        return daoProvider;
    }
}
