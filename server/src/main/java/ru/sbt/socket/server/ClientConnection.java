package ru.sbt.socket.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * Created by kirill on 12.09.16
 */
public class ClientConnection implements Runnable {
    private final Socket client;
    private final Object impl;

    public ClientConnection(Socket client, Object impl) {
        this.client = client;
        this.impl = impl;
    }

    @Override
    public void run() {
        ObjectOutputStream stream = null;
        try (ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
             ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream())) {
            stream = oos;
            String methodName = (String) ois.readObject();
            Object[] args = (Object[]) ois.readObject();
            Class<?>[] params = (Class<?>[]) ois.readObject();

            Method method = impl.getClass().getMethod(methodName, params);
            Object result = method.invoke(impl, args);
            oos.writeObject(result);
            stream.close();
            client.close();
        } catch (Exception e) {
            if (stream != null)
                try {
                    stream.writeObject(e);
                } catch (IOException e1) {
                    throw new RuntimeException("Exception happened during work with socket", e1);
                }
        }
    }
}
