1. pom.xml添加依赖，主要添加netty-socketio依赖
```xml
<dependency>
    <groupId>com.corundumstudio.socketio</groupId>
    <artifactId>netty-socketio</artifactId>
    <version>1.7.18</version>
</dependency>
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.62</version>
</dependency>
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <scope>provided</scope>
    <version>1.18.10</version>
</dependency>
```
2.添加自定义配置netty-socket-io.properties
```xml
# host在本地测试可以设置为本机IP(设置为localhost时局域网无法通过ip访问socket)，在Linux服务器跑可换成服务器内网IP
#socketio.host=127.0.0.1
socketio.host=192.168.0.102
socketio.port=9099
# 设置最大每帧处理数据的长度，防止他人利用大数据来攻击服务器
socketio.maxFramePayloadLength=1048576
# 设置http交互最大内容长度
socketio.maxHttpContentLength=1048576
# socket连接数大小（如只监听一个端口boss线程组为1即可）
socketio.bossCount=1
socketio.workCount=100
socketio.allowCustomRequests=true
# 协议升级超时时间（毫秒），默认10秒。HTTP握手升级为ws协议超时时间
socketio.upgradeTimeout=1000000
# Ping消息超时时间（毫秒），默认60秒，这个时间间隔内没有接收到心跳消息就会发送超时事件
socketio.pingTimeout=6000000
# Ping消息间隔（毫秒），默认25秒。客户端向服务器发送一条心跳消息间隔
socketio.pingInterval=25000
socketio.context=/game
```
3.添加配置类
```java
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/netty-socket-io.properties")
public class SocketIOConfig {

    @Value("${socketio.host}")
    private String host;

    @Value("${socketio.port}")
    private Integer port;

    @Value("${socketio.bossCount}")
    private int bossCount;

    @Value("${socketio.workCount}")
    private int workCount;

    @Value("${socketio.allowCustomRequests}")
    private boolean allowCustomRequests;

    @Value("${socketio.upgradeTimeout}")
    private int upgradeTimeout;

    @Value("${socketio.pingTimeout}")
    private int pingTimeout;

    @Value("${socketio.pingInterval}")
    private int pingInterval;

    @Value("${socketio.context}")
    private String context;

    /**
     * 以下配置在上面的application.properties中已经注明
     */
    @Bean
    public SocketIOServer socketIOServer() {
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setTcpNoDelay(true);
        socketConfig.setSoLinger(0);
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setSocketConfig(socketConfig);
        config.setHostname(host);
        config.setPort(port);
        config.setBossThreads(bossCount);
        config.setWorkerThreads(workCount);
        config.setAllowCustomRequests(allowCustomRequests);
        config.setUpgradeTimeout(upgradeTimeout);
        config.setPingTimeout(pingTimeout);
        config.setPingInterval(pingInterval);
        config.setContext(context);
        return new SocketIOServer(config);
    }
}
```
4.添加netty-socketio服务类
```java
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.corundumstudio.socketio.BroadcastOperations;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.lwl.base.websocket.service.SocketIOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author linwenli
 */
@Service
public class SocketIOServiceImpl implements SocketIOService {

    @Autowired
    private SocketIOServer socketIOServer;

    /**用户与服务器建立SocketIO连接的userId集合，id作为key，SocketIOClient的SessionId为value*/
    private static Map<String, UUID> connUserIdMap = new ConcurrentHashMap<>();


    /**
     * Spring IoC容器创建之后，在加载SocketIOServiceImpl Bean之后启动
     */
    @PostConstruct
    @Override
    public void start() {
        // 监听客户端连接
        socketIOServer.addConnectListener(client -> {
            String userId = getParamsByClient(client);
            if (!StringUtils.isEmpty(userId)) {
                // 若用户已有连接，断开旧连接
                if (connUserIdMap.containsKey(userId)) {
                    UUID uuid = connUserIdMap.get(userId);
                    if (uuid != null) {
                        socketIOServer.getClient(uuid).disconnect();
                    }
                }
                // 将客户端id存入集合
                connUserIdMap.put(userId, client.getSessionId());
                System.out.println(String.format("新增连接 userId:%s; SocketIoClientSessionId:%s", userId, client.getSessionId()));
            } else {
                System.out.println(String.format("新增连接失败 userId不能为空; SocketIoClientSessionId:%s", client.getSessionId()));
            }
        });

        // 监听客户端断开连接
        socketIOServer.addDisconnectListener(client -> {
            String userId = getParamsByClient(client);
            if (!StringUtils.isEmpty(userId)) {
                client.disconnect();
                // 移出ClientId集合，断开连接
                connUserIdMap.remove(userId);
                System.out.println(String.format("断开连接 userId:%s; SocketIoClientSessionId:%s", userId, client.getSessionId()));
            } else {
                System.out.println(String.format("断开连接失败 userId不能为空; SocketIoClientSessionId:%s", client.getSessionId()));
            }
        });

        // 自定义监听事件，接收处理前端传到服务端的数据，eventName要与前端的事件名一致
        socketIOServer.addEventListener("clientMsgEvent", Object.class, (client, data, ackSender) -> {
            // 执行对应的操作
            String userId = getParamsByClient(client);
            System.out.println(String.format("收到用户：%s 的数据：%s", userId, JSONObject.toJSONString(data)));
            // 返回处理结果
            client.sendEvent("serverMsgEvent", String.format("userId:%s，你好！收到你发送的数据：%s", userId, JSONObject.toJSONString(data)));
        });

        // 服务启动
        socketIOServer.start();
    }

    /**
     * Spring IoC容器在销毁SocketIOServiceImpl Bean之前关闭,避免重启项目服务端口占用问题
     */
    @PreDestroy
    @Override
    public void stop() {
        if (socketIOServer != null) {
            socketIOServer.stop();
            socketIOServer = null;
        }
    }

    @Override
    public void pushRoomMessage(String eventName, String roomId, Object msgData) {
        BroadcastOperations roomOperations = socketIOServer.getRoomOperations(roomId);
        if (roomOperations != null) {
            roomOperations.sendEvent(eventName, msgData);
            System.out.println("推送群消息：" + eventName + "," + JSON.toJSON(msgData));
        }
    }

    @Override
    public void pushUserIdMessage(String eventName, String userId, Object msgData) {
        if (!StringUtils.isEmpty(userId) && connUserIdMap.containsKey(userId)) {
            UUID uuid = connUserIdMap.get(userId);
            if (uuid != null) {
                SocketIOClient client = socketIOServer.getClient(uuid);
                if (client == null) {
                    return;
                }
                client.sendEvent("message", JSONObject.toJSONString(msgData));
                System.out.println("推送个人消息：" + eventName + "," + JSON.toJSON(msgData));
            }
        }
    }


    /**
     * 获取client连接中的指定参数
     * @param client   SocketIO的客户端对象
     * @return 参数值
     */
    private String getParamsByClient(SocketIOClient client) {
        // 从请求的连接中取出参数
        Map<String, List<String>> params = client.getHandshakeData().getUrlParams();
        List<String> list = params.get("userId");
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

}
```
5.编写html客户端
```html
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>NETTY SOCKET.IO DEMO</title>
    <base>
	<script src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
	<script src="https://cdn.bootcss.com/socket.io/2.1.0/socket.io.js"></script>
    <style>
        body {
            padding: 20px;
        }
        #console {
            height: 450px;
            overflow: auto;
        }
        .username-msg {
            color: orange;
        }
        .connect-msg {
            color: green;
        }
        .disconnect-msg {
            color: red;
        }
    </style>
</head>

<body>
    <div id="console" class="well"></div>
	<input id="msg" type="text" />
    <button id="submit" type="button">发送</button>

    <button id="joinBtn" type="button">进入房间</button>
    <button id="leaveBtn" type="button">离开房间</button>
</body>
<script type="text/javascript">
    var socket;

    $("#joinBtn").on("click",function (event) {
        if (socket === null){
            console.log("进入房间");
            connectSocketIO();
            alert(socket.isConnected());
        }else {

        }

    });
    $("#leaveBtn").on("click",function (event) {
        console.log("离开房间");
        socket.disconnect();
    });

    /*连接socketio*/
    function connectSocketIO() {
        socket = io.connect('ws://192.168.0.102:9099?userId=1', {path: '/game'});
		//定义监听事件及事件内容(当服务端调用匹配事件时，客户端执行事件内容)
        socket.on('connect', function () {
            console.log("socket已连接");
            output('<span class="connect-msg">连接成功</span>');
        });
        socket.on('disconnect', function () {
            console.log("socket已断开");
            output('<span class="disconnect-msg">' + '已下线! </span>');
        });

        // 自定义事件
        socket.on('serverMsgEvent', function (data) {
            console.log(data);
            output('<span class="username-msg">' + data + ' </span>');
        });
    }

    /*输出信息*/
    function output(message) {
        var element = $("<div>" + message + "</div>");
        $('#console').prepend(element);
    }

	$("#submit").on("click",function(){
        const $msg = $("#msg");
        const msg = $msg.val();
        if (msg != null && msg !== ""){
            // 指定事件名，提交数据到服务器，服务器端监听clientMsgEvent事件将会收到数据并处理
            socket.emit("clientMsgEvent", msg);
            $msg.val("");
        }else {
            alert("不能发送空数据");
        }
	})

</script>
</html>
```