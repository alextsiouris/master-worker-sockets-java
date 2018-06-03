/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator.worker;

import java.util.concurrent.Callable;

/**
 *
 * @author ptzafos
 */
public class CalculatorThread implements Callable<Integer> {

	int start;
	int step;
	int[] split;

	public CalculatorThread(int start, int step, int[] split) {
		this.start = start;
		this.step = step;
		this.split = split;
	}

	@Override
	public Integer call() throws Exception {
		return 2;
	}

}
