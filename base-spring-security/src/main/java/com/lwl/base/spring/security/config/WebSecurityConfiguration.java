package com.lwl.base.spring.security.config;

import com.lwl.base.spring.security.config.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 设置默认的加密方式
        return new BCryptPasswordEncoder();
    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService());
//    }
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        // 将 check_token 暴露出去，否则资源服务器访问时报 403 错误
//        web.ignoring().antMatchers("/oauth/check_token");
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.formLogin()
//                .loginPage("/loginPage")
//                .loginProcessingUrl("/login");
////        ((HttpSecurity)((HttpSecurity)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)http.authorizeRequests().anyRequest()).authenticated().and()).formLogin().and()).httpBasic();
////        http
////                // 必须配置，不然OAuth2的http配置不生效----不明觉厉
////                .requestMatchers()
////                .antMatchers("/auth/login", "/auth/authorize","/oauth/authorize")
////                .and()
////                .authorizeRequests()
////                // 自定义页面或处理url时，如果不配置全局允许，浏览器会提示服务器将页面转发多次
////                .antMatchers("/auth/login", "/auth/authorize")
////                .permitAll()
////                .anyRequest()
////                .authenticated();
////
////        // 表单登录
////        http.formLogin()
////                // 登录页面
////                .loginPage("/auth/login")
////                // 登录处理url
////                .loginProcessingUrl("/auth/authorize");
////        http.httpBasic().disable();
//        http.csrf().disable();
//        http.antMatcher("/oauth/**")
//                .authorizeRequests()
//                .antMatchers("/oauth/index").permitAll()
//                .antMatchers("/oauth/token").permitAll()
//                .antMatchers("/oauth/check_token").permitAll()
//                .antMatchers("/oauth/confirm_access").permitAll()
//                .antMatchers("/oauth/error").permitAll()
//                .antMatchers("/oauth/approvale/confirm").permitAll()
//                .antMatchers("/oauth/approvale/error").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/auth/login")
//                .loginProcessingUrl("/auth/authorize");
//    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/views/**").hasAuthority("SystemContentView")
                .antMatchers("/", "/home").permitAll()
                .antMatchers("/swagger-ui.html" ).hasAuthority("SystemContentView1")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }
}
