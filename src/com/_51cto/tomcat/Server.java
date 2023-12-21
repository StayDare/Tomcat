package com._51cto.tomcat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
    主启动类。
    创建ServerSocket,等待客户端连接,线程池维护
    死循环等待下一个客户连接
 */
public class Server {
    private static int port = 8080;
    private static ServerSocket serverSocket = null;
    private final static int POOL_SIZE = 8;
    private static ExecutorService executorService = null;

    public static void start(){
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("服务器已启动");
            executorService = Executors.newFixedThreadPool(POOL_SIZE);
            while (true){
                Socket socket = serverSocket.accept();
                System.out.println("已经接收到请求");
                //分配线程处理请求
                executorService.execute(new MyHandler(socket));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        start();
    }
}
