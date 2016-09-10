package ru.sbt.socket.server;

import ru.sbt.socket.impls.CalculatorImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirill on 08.09.16
 */
public class ServerRegistrator {
    public static void listen(String host, int port, Object impl) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            String methodName = null;
            ObjectOutputStream stream = null;
            List<Exception> exceptions = new ArrayList<>();
            while (true) {
                try (Socket client = serverSocket.accept();
                     ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
                     ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream())) {
                    stream = oos;
                    methodName = (String) ois.readObject();
                    Object[] args = (Object[]) ois.readObject();
                    Class<?>[] params = (Class<?>[]) ois.readObject();

                    Method method = impl.getClass().getMethod(methodName, params);
                    Object result = method.invoke(impl, args);
                    oos.writeObject(result);
                } catch (Exception e) {
                    exceptions.add(e);
                } finally {
                    //sendExceptions(exceptions, host, port);
//                    if (stream != null)
//                        stream.writeObject(exceptions);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Exception happened during work with socket", e);
        }
    }

    public static void main(String[] args) {
        listen("127.0.0.1", 9090, new CalculatorImpl());
    }

    private static void sendExceptions(List exceptions, String host, int port) {
        try (Socket socket = new Socket(host, port);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
            oos.writeObject(exceptions);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
