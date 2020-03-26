package com.lwl.base.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
public class RedisConfiguration {

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
