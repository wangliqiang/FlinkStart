package com.socket;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnEvent;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/25 16:35
 */
public class EventListenner {

    SocketIOClientCache clientCache = new SocketIOClientCache();
    SocketIOResponse socketIOResponse = new SocketIOResponse();

    @OnConnect
    public void onConnect(SocketIOClient client) {
        System.out.println(client.getHandshakeData().getUrlParams());
        System.out.println("连接成功");
    }

    @OnEvent("OnMSG")
    public void onSync(SocketIOClient client, MsgBean bean) {
        System.out.printf("recived msg - from: %s to:%s\n", bean.getFrom(), bean.getTo());
        clientCache.addClient(client, bean);
        SocketIOClient ioClients = clientCache.getClient(bean.getTo());
        System.out.println("clientCache");
        if (ioClients == null) {
            System.out.println("你发送消息的用户不在线");
            return;
        }
        socketIOResponse.sendEvent(ioClients, bean);
    }
}
