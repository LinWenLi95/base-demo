package com.lwl.base.mysql;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.lwl.base.mysql.dao")
public class BaseMysqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseMysqlApplication.class, args);
    }
}
