/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator.worker;

import java.util.concurrent.Callable;

/**
 * @author ptzafos
 */
public class CalculatorThread implements Callable<Double> {

    int start;
    int step;
    int[] split;

    public CalculatorThread(int start, int step, int[] split) {
        this.start = start;
        this.step = step;
        this.split = split;
    }

    @Override
    public Double call() throws Exception {
        Double sum = 0.0;
        for (int i = 0; i < split.length; i++) {
            sum += split[i];
        }
        return sum;
    }
}
