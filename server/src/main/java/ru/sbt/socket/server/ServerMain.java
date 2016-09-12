package ru.sbt.socket.server;

import ru.sbt.socket.impls.CalculatorImpl;

/**
 * Created by kirill on 12.09.16
 */
public class ServerMain {
    public static void main(String[] args) {
        ServerRegistrator registrator = new ServerRegistrator();
        registrator.listen(9090, new CalculatorImpl());
    }
}
