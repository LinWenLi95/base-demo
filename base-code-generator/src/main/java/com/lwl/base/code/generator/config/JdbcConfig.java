package com.lwl.base.code.generator.config;

import lombok.Data;

/**
 * JDBC连接参数配置
 * @author Administrator
 */
@Data
public class JdbcConfig {
    private String driverClass;
    private String url;
    private String userName;
    private String password;
}
