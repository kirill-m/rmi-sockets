package ru.sbt.socket.client;

import ru.sbt.socket.common_interfaces.Calculator;

/**
 * Created by kirill on 08.09.16
 */
public class ClientMain {
    public static void main(String[] args) {
        Calculator calculator = new NetClientFactory("127.0.0.1", 9090).createClient(Calculator.class);
        Double result = calculator.calculate(1, 2);
        System.out.println("Final result: " + result);
    }
}
