package com.lwl.base.redis.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/{key}/{value}")
    public String set(@PathVariable("key") String key, @PathVariable("value") String value) {
        redisTemplate.opsForValue().set(key,value);
        return "success";
    }

    @GetMapping("/{key}")
    public String get(@PathVariable("key") String key) {
        String value = redisTemplate.opsForValue().get(key);
        return value == null ? "value is null" : value;
    }
}
