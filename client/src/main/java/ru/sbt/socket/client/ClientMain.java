package ru.sbt.socket.client;

import ru.sbt.socket.common_interfaces.Calculator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirill on 08.09.16
 */
public class ClientMain {
    public static void main(String[] args) {
        Calculator calculator = new NetClientFactory("127.0.0.1", 9090).createClient(Calculator.class);

        List<Double> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(calculator.calculate(1, 2));
        }
        list.forEach(System.out::println);
    }
}
