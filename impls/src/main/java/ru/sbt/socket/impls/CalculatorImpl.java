package ru.sbt.socket.impls;

import ru.sbt.socket.common_interfaces.Calculator;

/**
 * Created by kirill on 08.09.16
 */
public class CalculatorImpl implements Calculator {
    @Override
    public Double calculate(Integer a, Integer b) {
        return a + b - 410D / 12;
    }
}
