package ru.sbt.socket.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.List;

/**
 * Created by kirill on 08.09.16
 */
public class ClientInvocationHandler implements InvocationHandler {
    private final String host;
    private final int port;

    public ClientInvocationHandler(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try (Socket socket = new Socket(host, port);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
            oos.writeObject(method.getName());
            oos.writeObject(args);
            Class<?>[] params = method.getParameterTypes();
            oos.writeObject(params);

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Object result = ois.readObject();
//            List<Exception> exceptions;
//            if (ois.available() > 0) {
//                exceptions =  (List<Exception>) ois.readObject();
//                throwException(exceptions);
//                return result;
//            }

            return result;
        } catch (IOException e) {
            throw new RuntimeException("Exception happened during working with socket", e);
        }
    }

    private void throwException(List<Exception> list) {
        if (list == null)
            return;

        for (Exception e : list) {
            switch (e.getClass().getSimpleName()) {
                case "NoSuchMethodException":
                    throw new RuntimeException("Method has not been found", e);
                case "InvocationTargetException":
                    throw new RuntimeException("InvocationTargetException ignore", e);
                case "IllegalAccessException":
                    throw new RuntimeException("Method does not have access to the definition of method", e);
                case "ClassNotFoundException":
                    throw new RuntimeException("Exception while trying to cast class", e);
            }
        }
    }
}
