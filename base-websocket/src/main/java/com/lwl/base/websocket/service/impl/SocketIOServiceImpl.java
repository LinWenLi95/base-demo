package com.lwl.base.websocket.service.impl;

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
