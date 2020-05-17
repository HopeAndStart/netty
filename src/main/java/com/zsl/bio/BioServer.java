package com.zsl.bio;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * BIO网络编程服务端
 *
 * @author zsl
 * @version 1.0.0
 * @date: 2020/5/17 20:41
 **/
public class BioServer {
    private static Executor bioThreadPool;
    
    static {
        Executors.newCachedThreadPool();
        bioThreadPool = new ThreadPoolExecutor(10, 100,
                10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(200), ThreadFactory::createThread);
    }
    
    public static void main (String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("服务端启动监听端口8080");
        while (true) {
            // 监听客户端
            final Socket socket = serverSocket.accept();
            System.out.println("链接到了客户端");
            // 启动线程通信
            bioThreadPool.execute(() -> {
                try (InputStream inputStream = socket.getInputStream())
                {
                    byte[] bytes = new byte[1024];
                    while (true) {
                        String name = Thread.currentThread().getName();
                        System.out.println(name);
                        inputStream.read(bytes);
                        System.out.println(new String(bytes));
                    }
                }catch (Exception e){
                    System.out.println("发生了异常");
                }
            });
        }
    }
    
}

class ThreadFactory {
    public static Thread createThread (Runnable r) {
        Thread resultThread = new Thread(r);
        resultThread.setName("BIO_THREAD_" + UUID.randomUUID().toString().replaceAll("-", ""));
        return resultThread;
    }
}