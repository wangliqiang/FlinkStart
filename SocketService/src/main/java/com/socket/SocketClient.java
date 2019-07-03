package com.socket;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/25 16:48
 */
public class SocketClient {
    public static void main(String[] args) throws Exception {
        IO.Options options = new IO.Options();
        options.transports = new String[]{"websocket"};
        options.reconnectionAttempts = 2;
        options.reconnectionDelay = 1000;//失败重连的时间间隔
        options.timeout = 500;//连接超时时间(ms)
        //par1 是任意参数
        Socket socket = IO.socket("http://localhost:8000",options);

        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

            public void call(Object... args) {
                socket.send("connected");
            }
        });

        //自定义事件
        socket.on("connected", new Emitter.Listener() {
            public void call(Object... objects) {
                System.out.println("receive connected data:" + objects[0].toString());
            }
        });

        socket.connect();
        //循环发送数据
        while (true){
            socket.emit("client_info"," 客户端在发送数据");
            Thread.sleep(2000);
        }
    }
}
