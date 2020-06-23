package com.lwl.demo.mq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * @author LinWenLi
 * @since 2020-06-23
 */
@Component
public class MqReceiver implements ChannelAwareMessageListener {

    @RabbitListener(queues = "testQueue1")
    public void onMessage1(Message message, Channel channel) throws Exception {
        String data = new String(message.getBody());
        System.out.println("testQueue1接收到的数据：" + data);
    }

    @RabbitListener(queues = "testQueue2")
    public void onMessage2(Message message, Channel channel) throws Exception {
        String data = new String(message.getBody());
        System.out.println("testQueue2接收到的数据：" + data);
    }

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {

    }
}
