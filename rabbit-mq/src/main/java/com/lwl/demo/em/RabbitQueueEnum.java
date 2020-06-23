package com.lwl.demo.em;

import lombok.Getter;

/**
 * 消息队列枚举
 * @author LinWenLi
 * @since 2020-06-23
 */
@Getter
public enum RabbitQueueEnum {

    DIRECT_QUEUE1_ROUTING1("testDirectExchange","queue1","TestDirectRouting"),
    DIRECT_QUEUE2_ROUTING1("testDirectExchange","queue2","TestDirectRouting"),
    QUEUE_3("testDirectExchange","queue3","TestDirectRouting"),
    NONE(null, null, null);

    /**交换机名*/
    private final String exchangeName;
    /**队列名*/
    private final String queueName;
    /**路由key*/
    private final String routingKey;

    RabbitQueueEnum(String exchangeName, String queueName, String routingKey) {
        this.exchangeName = exchangeName;
        this.queueName = queueName;
        this.routingKey = routingKey;
    }
}
