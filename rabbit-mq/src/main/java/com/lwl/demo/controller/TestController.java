package com.lwl.demo.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LinWenLi
 * @since 2020-06-23
 */
@RestController
public class TestController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("put")
    public String put(String msg){
//        https://blog.csdn.net/qq_35387940/article/details/100514134
//        rabbitTemplate.convertAndSend("testDirectExchange", "TestDirectRouting", msg);
//        rabbitTemplate.convertAndSend("testFanoutExchange", null, msg);
        rabbitTemplate.convertAndSend("testTopicExchange", "TestTopicRouting", msg);
        return "success";
    }
}
