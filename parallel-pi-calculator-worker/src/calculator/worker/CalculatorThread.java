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
    int total;

    public CalculatorThread(int start, int step, int total) {
        this.start = start;
        this.step = step;
        this.total = total;
    }

    @Override
    public Double call() throws Exception {
        Double result = 0.0;

        double deltaX = 1.0/total;

        for ( int i=start; i < start+step; i++ )
            result += f( i*deltaX );

        result *= deltaX;
        return result;
    }

    public double f( double x ) {
        return 4.0 / ( 1 + x*x );
    }
}
