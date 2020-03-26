## springboot整合redis

* 1.本地运行redis服务
* 2.pom.xml添加依赖
```xml
 <dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-web</artifactId>
 </dependency>

 <dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-test</artifactId>
 </dependency>

 <dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-data-redis</artifactId>
 </dependency>
 <!-- https://mvnrepository.com/artifact/redis.clients/jedis -->
 <dependency>
     <groupId>redis.clients</groupId>
     <artifactId>jedis</artifactId>
 </dependency>
```
* 3.application.yml添加redis配置
```yaml
spring:
  #redis settings
  redis:
    host: 127.0.0.1
    port: 6379
    password:
    jedis:
      pool:
        max-active: 8
        max-wait: -1
        max-idle: 500
        min-idle: 0
    lettuce:
      shutdown-timeout: 0
```
* 4.添加配置类
```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
public class ReidsConfiguration {

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate() {
        RedisTemplate<String, String> stringRedisTemplate = new RedisTemplate<>();
        stringRedisTemplate.setConnectionFactory(jedisConnectionFactory());
        // 设置key序列化和反序列化类型
        stringRedisTemplate.setKeySerializer(new Jackson2JsonRedisSerializer<>(String.class));
        // 设置value序列化和反序列化类型
        stringRedisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));
        return stringRedisTemplate;
    }
}
```
* 5.编写测试类
```java
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.Set;

@SpringBootTest
public class TestApplication {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void test() {
        redisTemplate.opsForValue().set("abc:hahahha","def");
        System.out.println(redisTemplate.opsForValue().get("abc:hahahha"));
    }
}
```