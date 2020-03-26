package com.lwl.base.redis;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.Set;

@SpringBootTest
public class WebAdminApplication {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void test() {
        redisTemplate.opsForValue().set("abc:hahahha","def");
        redisTemplate.opsForValue().set("abc:hghhhh","defdfdf");
        Set<String> abc = redisTemplate.keys("abc:*");
        assert abc != null;
        for (String s : abc) {
            System.out.println(s);
        }
        System.out.println(redisTemplate.opsForValue().get("abc:hahahha"));
    }
}
