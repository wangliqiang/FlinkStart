package com.socket;

import com.corundumstudio.socketio.SocketIOClient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/25 16:35
 */
public class SocketIOClientCache {
    //String：EventType类型
    private Map<String, SocketIOClient> clients = new ConcurrentHashMap<String, SocketIOClient>();

    //用户发送消息添加
    public void addClient(SocketIOClient client, MsgBean msgBean) {
        clients.put(msgBean.getFrom(), client);
    }

    //用户退出时移除
    public void remove(MsgBean msgBean) {
        clients.remove(msgBean.getFrom());
    }

    //获取所有
    public SocketIOClient getClient(String to) {
        return clients.get(to);
    }
}
