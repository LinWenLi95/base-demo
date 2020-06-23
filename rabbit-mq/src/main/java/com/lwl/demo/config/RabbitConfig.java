package com.lwl.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mq配置  直连交换机
 * @author LinWenLi
 * @since 2020-06-23
 */
@Configuration
public class RabbitConfig {

    /**
     * Direct交换机 起名：testDirectExchange
     */
    @Bean
    DirectExchange testDirectExchange(){
        return new DirectExchange("testDirectExchange", true, false);
    }

    /**
     * Fanout交换机 起名：testFanoutExchange
     */
    @Bean
    FanoutExchange testFanoutExchange(){
        return new FanoutExchange("testFanoutExchange", true, false);
    }

    /**
     * Topic交换机 起名：testTopicExchange
     */
    @Bean
    TopicExchange testTopicExchange(){
        return new TopicExchange("testTopicExchange", true, false);
    }

    /**
     * 队列
     * @return
     */
    @Bean
    Queue testQueue1(){
        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
        // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
        //   return new Queue("TestDirectQueue",true,true,false);

        //一般设置一下队列的持久化就好,其余两个就是默认false
        return new Queue("testQueue1", true);
    }

    /**
     * 队列
     * @return
     */
    @Bean
    Queue testQueue2(){
        return new Queue("testQueue2", true);
    }

    /**
     * 绑定交换机和队列
     * @return
     */
    @Bean
    Binding bindingDirect1(){
        return BindingBuilder.bind(testQueue1()).to(testDirectExchange()).with("TestDirectRouting");
    }
    @Bean
    Binding bindingDirect2(){
        return BindingBuilder.bind(testQueue2()).to(testDirectExchange()).with("TestDirectRouting");
    }

    @Bean
    Binding bindingFanout1(){
        return BindingBuilder.bind(testQueue1()).to(testFanoutExchange());
    }

    @Bean
    Binding bindingFanout2(){
        return BindingBuilder.bind(testQueue2()).to(testFanoutExchange());
    }

    @Bean
    Binding bindingTopic1(){
        return BindingBuilder.bind(testQueue1()).to(testTopicExchange()).with("TestTopicRouting");
    }
    @Bean
    Binding bindingTopic2(){
        return BindingBuilder.bind(testQueue2()).to(testTopicExchange()).with("TestTopicRouting");
    }

    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);

        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("ConfirmCallback:     "+"相关数据："+correlationData);
                System.out.println("ConfirmCallback:     "+"确认情况："+ack);
                System.out.println("ConfirmCallback:     "+"原因："+cause);
            }
        });

        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("ReturnCallback:     "+"消息："+message);
                System.out.println("ReturnCallback:     "+"回应码："+replyCode);
                System.out.println("ReturnCallback:     "+"回应信息："+replyText);
                System.out.println("ReturnCallback:     "+"交换机："+exchange);
                System.out.println("ReturnCallback:     "+"路由键："+routingKey);
            }
        });

        return rabbitTemplate;
    }
}
