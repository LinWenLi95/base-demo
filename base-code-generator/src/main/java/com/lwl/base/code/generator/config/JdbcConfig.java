package com.lwl.base.code.generator.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JDBC连接参数配置
 * @author Administrator
 */
@Data
@Component
@ConfigurationProperties(prefix = "code-generator.db")
public class JdbcConfig {
    private String driverClass;
    private String url;
    private String userName;
    private String password;
}
