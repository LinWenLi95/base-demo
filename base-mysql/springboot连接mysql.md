## springboot连接mysql
>mysql数据库
>>Mybatis
>>
>>Mybatisplus
>>
>>HikariCP

* 1.pom.xml添加依赖
```xml
<!--数据库连接 start-->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.19</version>
</dependency>
<dependency>
    <groupId>com.zaxxer</groupId>
    <artifactId>HikariCP</artifactId>
    <version>3.4.1</version>
</dependency>
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.2.0</version>
</dependency>
<!--数据库连接 end-->
```
* 2.application.yml配置数据库相关参数
```yaml
spring:
  #数据库
  datasource:
    type: com.zaxxer.hikari.HikariDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    jdbcUrl: jdbc:mysql://127.0.0.1:3306/db_test?useUnicode=true&characterEncoding=utf-8&useSSL=true
    url: jdbc:mysql://127.0.0.1:3306/db_test?useUnicode=true&characterEncoding=utf-8&useSSL=true
    username: root
    password: MysqL@951Qwd1C
    #hikari配置
    hikari:
      minimum-idle: 5
      idle-timeout: 600000
      maximum-pool-size: 10
      auto-commit: true
      pool-name: MyHikariCP
      max-lifetime: 1800000
      connection-timeout: 30000
      connection-test-query: SELECT 1
mybatis:
  type-aliases-package: com.lwl.base.mysql.pojo
  mapper-locations: classpath:mapper/*.xml
```
* 2.创建表映射类及mapper接口继承com.baomidou.mybatisplus.core.mapper.BaseMapper接口
```java
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* 系统 菜单表实例
* @author lwl
* @since 2019/12/15
*/
@Data
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**自增id*/
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**父菜单id（一级菜单的父id默认为0）*/
    private Integer parentId;
    /**菜单中文名称*/
    private String name;
    /**菜单英文名称*/
    private String enname;
    /**url地址(http请求类型全部为GET)*/
    private String url;
    /**菜单图标样式*/
    private String icon;
    /**状态 0禁用,1启动*/
    private Integer state;
    /**是否已删除*/
    private Boolean isDel;
    /**备注*/
    private String description;
    /**创建者id*/
    private Integer creatorId;
    /**创建时间*/
    private Timestamp createTime;
    /**更新者id*/
    private Integer updaterId;
    /**更新时间*/
    private Timestamp updateTime;

    /**是否根节点*/
    public boolean isRoot() {
        return parentId.equals(0);
    }
}
```

```java
package com.lwl.base.mysql.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lwl.base.mysql.pojo.SysMenu;

public interface SysMenuMapper extends BaseMapper<SysMenu> {

}
```
* 3.在启动类中添加扫描表映射mapper接口的注解@MapperScan,指定扫描的包路径
```java
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
```
* 4.编写查询的单元测试
```java
import com.lwl.base.mysql.dao.SysMenuMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class MysqlApplication {

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Test
    public void test() {
        Integer count = sysMenuMapper.selectCount(null);
        System.out.println(String.format("count:%s", count));
    }
}
```