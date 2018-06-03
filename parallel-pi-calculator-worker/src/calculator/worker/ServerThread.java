/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator.worker;

import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 *
 * @author ptzafos
 */
public class ServerThread implements Runnable {

	ServerSocket serverSocket;

	public ServerThread(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	@Override
	public void run() {
		while (true) {
			try {
				ArrayList<FutureTask<Integer>> results = new ArrayList<FutureTask<Integer>>();
				Socket dataSocket = serverSocket.accept();
				ObjectInputStream incomingStream = new ObjectInputStream(dataSocket.getInputStream());
				int[] request = (int[]) incomingStream.readObject();
				OutputStream outputStream = dataSocket.getOutputStream();
				int step = request.length / 3;
				int lastStep = request.length % 3;
				for (int i = 0; i < 3; i++) {
					Callable<Integer> result;
					if(i!=2) {
						result = new CalculatorThread(i * step, step, request);
					} else {
						result = new CalculatorThread(i * step, lastStep, request);
					}
					results.add(new FutureTask<Integer>(result));
					Thread t = new Thread(results.get(i));
					t.start();
				}
				Float response = results.stream().reduce(0.0, (a,b) -> a+b);
				PrintWriter responseBuilder = new PrintWriter(outputStream, true);
				responseBuilder.println(response);
				dataSocket.close();

			} catch (Exception exc) {
				System.out.println("Error with incoming connection, keep on working...");
				exc.printStackTrace();
			}
		}
	}

}
