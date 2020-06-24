### SpringBoot整合RabbitMq

#### 1. 安装mq服务
    CentOS7 Docker安装
    阿里云docker加速镜像仓库配置
    RabbitMq安装
#### 2. pom.xml添加mq的Maven依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
    <version>2.2.5.RELEASE</version>
</dependency>
```
#### 3. application.yml添加mq配置
#### 4. 添加RabbitConfig配置类
    * 创建交换机
    * 创建队列
    * 绑定交换机和队列，设置路由key
#### 5. 创建生产者发送消息到mq服务
#### 6. 创建消费者监听队列消息并手动确认

#### 消息模型种类
* 简单模式：1生产 1消费者
* 工作模式：1生产 多消费者（一条消息只会被消费一次）
* 订阅模式：1生产 （fanout交换机） 多消费者（一条消息会被多个消费者接收到）
* 路由模式：1生产  （direct交换机） 多消费者 （一条消息会被同路由key的多个消费者接收到）
* 通配符模式：1生产 （topic交换机）多消费者 （一条消息会被通配符匹配到的多个消费者接收到）

#### 生产者消息确认（confirm/return）

#### 消费者消息确认（自动/手动）

- Broker：简单来说就是消息队列服务器实体。
- Exchange：消息交换机，它指定消息按什么规则，路由到哪个队列。
- Queue：消息队列载体，每个消息都会被投入到一个或多个队列。
- Binding：绑定，它的作用就是把exchange和queue按照路由规则绑定起来。
- Routing Key：路由关键字，exchange根据这个关键字进行消息投递。
- vhost：虚拟主机，一个broker里可以开设多个vhost，用作不同用户的权限分离。
- producer：消息生产者，就是投递消息的程序。
- consumer：消息消费者，就是接受消息的程序。
- channel：消息通道，在客户端的每个连接里，可建立多个channel，每个channel代表一个会话任务。

消息队列的使用过程大概如下：

1. 客户端连接到消息队列服务器，打开一个channel。
1. 客户端声明一个exchange，并设置相关属性。
1. 客户端声明一个queue，并设置相关属性。
1. 客户端使用routing key，在exchange和queue之间建立好绑定关系。
1. 客户端投递消息到exchange。