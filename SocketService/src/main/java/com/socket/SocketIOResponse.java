package com.socket;

import com.corundumstudio.socketio.SocketIOClient;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/25 16:35
 */
public class SocketIOResponse {
    public void sendEvent(SocketIOClient client, MsgBean bean) {
        System.out.println("推送消息");
        client.sendEvent("OnMSG", bean);
    }
}
