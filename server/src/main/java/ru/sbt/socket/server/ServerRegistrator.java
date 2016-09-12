package ru.sbt.socket.server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by kirill on 08.09.16
 */
public class ServerRegistrator {
    final private ExecutorService executor = Executors.newFixedThreadPool(10);

    public void listen(int port, Object impl) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                try {
                    Socket client = serverSocket.accept();
                    executor.execute(new ClientConnection(client, impl));
                } catch (IOException e) {
                    throw new RuntimeException("Exception happened during socket accepting", e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Exception happened during work with socket", e);
        }
    }
}
