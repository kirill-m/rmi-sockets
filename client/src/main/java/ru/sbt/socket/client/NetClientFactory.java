package ru.sbt.socket.client;

import static java.lang.reflect.Proxy.newProxyInstance;

/**
 * Created by kirill on 08.09.16
 */
public class NetClientFactory {
    private final String host;
    private final int port;

    public NetClientFactory(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public <T> T createClient(Class<T> interfaceClass) {
        return (T) newProxyInstance(ClassLoader.getSystemClassLoader(),
                new Class[]{interfaceClass}, new ClientInvocationHandler(host, port));
    }
}
