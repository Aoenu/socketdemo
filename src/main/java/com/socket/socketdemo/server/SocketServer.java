package com.socket.socketdemo.server;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ServerEndpoint(value = "/socketServer/{userId}")
@Component
public class SocketServer {

    private Session session;
    private static Map<String, Session> sessionPool = new HashMap<String, Session>();
    private static Map<String, String> sessionIds = new HashMap<String, String>();

    /**
     * 用户连接时触发
     *
     * @param session
     * @param userid
     */
    @OnOpen
    public void open(Session session, @PathParam(value = "userId") String userid) {
        this.session = session;
        sessionPool.put(userid, session);
        sessionIds.put(session.getId(), userid);
    }

    /**
     * 收到信息时触发
     *
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        sendMessage(sessionIds.get(session.getId()) + "<--" + message, "niezhiliang9595");
        System.out.println("发送人:" + sessionIds.get(session.getId()) + "内容:" + message);
    }

    /**
     * 连接关闭触发
     */
    @OnClose
    public void onClose() {
        sessionPool.remove(sessionIds.get(session.getId()));
        sessionIds.remove(session.getId());
    }

    /**
     * 发生错误时触发
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    /**
     * 信息发送的方法
     *
     * @param message
     * @param userId
     */
    public static void sendMessage(String message, String userId) {
        Session s = sessionPool.get(userId);
        if (s != null) {
            try {
                s.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 多个人发送给指定的几个用户
     *
     * @param msg
     * @param persons 用户s
     */
    public void sendMsg(String msg, List<String> persons) {
        for (String userId : persons) {
            sendMessage(msg, userId);
        }
    }
}
