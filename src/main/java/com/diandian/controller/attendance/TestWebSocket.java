package com.diandian.controller.attendance;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/test/{id}")
public class TestWebSocket {

    private Integer id;
    private Session session;

    public TestWebSocket() {
        System.out.println(this);
    }

    @OnOpen
    public void open(Session session, @PathParam("id")Integer id) {
        System.out.println("新用户连接，id：" + id);
        this.id = id;
        this.session = session;
    }

    @OnMessage
    public void message(String message) {
        System.out.println("客户" + id + "发来消息：" + message);
        sendMessage("你是" + id + "\t this:" + this);
    }


    @OnClose
    public void close() {
        System.out.println("客户退出，id：" + id);
    }


    private void sendMessage(String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
