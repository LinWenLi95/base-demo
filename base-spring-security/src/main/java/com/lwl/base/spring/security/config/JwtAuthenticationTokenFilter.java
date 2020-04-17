package com.lwl.base.spring.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Jwt身份验证令牌过滤器
 * @author LinWenLi
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //获取JWT
        //校验JWT是否正确
        //获取JWT头部的subject/role信息
        //生成UsernamePasswordAuthenticationToken对象

        // 获取请求中携带的token
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER);
        if (!StringUtils.isEmpty(token) && token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            Jws<Claims> claimsJws = null;
            try {
                // 使用密钥解析JWT
                claimsJws = Jwts.parserBuilder()
                        .setSigningKey(SecurityConstants.JWT_SECRET.getBytes())
                        .build().parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, ""));
            }  catch (ExpiredJwtException e) {
                // 在这里处理token过期
                e.printStackTrace();
            }
            if (claimsJws != null) {
                // 获取用户名及授权列表
                Claims body = claimsJws.getBody();
                String username = body.getSubject();
                List<SimpleGrantedAuthority> authorities = ((List<?>) body.get("rol")).stream()
                        .map(authority -> new SimpleGrantedAuthority((String) authority))
                        .collect(Collectors.toList());
                if (!StringUtils.isEmpty(username)) {
                    // 组成授权令牌对象
                    Authentication authentication =  new UsernamePasswordAuthenticationToken(username, null, authorities);
                    // 将 authentication 存入 ThreadLocal，方便后续获取用户信息
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);
    }

}
