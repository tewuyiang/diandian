package com.diandian.controller.message;

import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.websocket.server.ServerEndpoint;

/**
 * websocket消息推送
 * 获取数据库中的消息发送给前台
 */
@ServerEndpoint(value = "/MessagePush/getMessage",configurator = SpringConfigurator.class)
public class MessagePush {



}
