package com.lwl.base.spring.security.config.jwt;

import com.lwl.base.spring.security.config.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 过滤器 登录时进行身份认证，认证成功后返回授权token
 * @author LinWenLi
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    /**认证管理器对象*/
    private final AuthenticationManager authenticationManager;

    /**
     * 过滤器构造方法 初始化认证管理器
     */
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL);
    }

    /**
     * 用户登录时获取请求中的用户名密码参数进行身份认证
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 取出登录请求中的用户名密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        // 将用户名密码构造成UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        // 将UsernamePasswordAuthenticationToken使用认证管理器进行身份认证
        return this.authenticationManager.authenticate(token);
    }

    /**
     * 身份认证成功则返回token
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        // 可以将这个key保存起来，要用时再生成SecretKey
        SecretKey secretKey = Keys.hmacShaKeyFor(SecurityConstants.JWT_SECRET.getBytes());
        String token = Jwts.builder()
                .setHeaderParam("TYP", SecurityConstants.TOKEN_TYPE)
                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .setSubject(user.getUsername())
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setIssuedAt(new Date())
                // 角色列表放入header
                .claim("rol", roles)
                .signWith(secretKey).compact();
        response.setHeader(SecurityConstants.TOKEN_HEADER,SecurityConstants.TOKEN_PREFIX + token);
    }

    /**
     * 生成jwt密钥
     * @return String
     */
    public static String generateJwtSecret() {
        // 生成key
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        // 可以将这个key保存起来，要用时再生成SecretKey
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

}
