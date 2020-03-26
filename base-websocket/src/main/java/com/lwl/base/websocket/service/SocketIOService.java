package com.lwl.base.websocket.service;

public interface SocketIOService {

    /**
     * 启动SocketIO服务
     */
    void start();

    /**
     * 停止SocketIO服务
     */
    void stop();

    /**
     * 推送消息 指定房间
     * @param eventName 客户端监听的事件名
     * @param roomId SocketIO的roomid
     * @param msgData 要推送的数据
     */
    void pushRoomMessage(String eventName, String roomId, Object msgData);

    /**
     * 推送消息 指定用户
     * @param eventName 客户端监听的事件名
     * @param userId 用户id
     * @param msgData 要推送的数据
     */
    void pushUserIdMessage(String eventName, String userId, Object msgData);

}
