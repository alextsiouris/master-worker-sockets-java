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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author ptzafos
 */
public class ServerThread implements Runnable {

    private ServerSocket serverSocket;

    public ServerThread(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    private static Double getResult(FutureTask<Double> result) {
        try {
            return result.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                //Create a list that handles the executions of calculation threads.
                ArrayList<FutureTask<Double>> results = new ArrayList<>();
                Socket dataSocket = serverSocket.accept();
                ObjectInputStream incomingStream = new ObjectInputStream(dataSocket.getInputStream());
                int[] request = (int[]) incomingStream.readObject();
                OutputStream outputStream = dataSocket.getOutputStream();
                int step = (request[1] - request[0]) / 3;
                //last spawned thread takes the rest
                int lastStep = (request[1] - request[0]) % 3;
                for (int i = 0; i < 4; i++) {
                    Callable<Double> result;
                    if (i != 3) {
                        result = new CalculatorThread((request[3] * request[2] / request[4]) + (i * step), step, request[2]);
                    } else {
                        result = new CalculatorThread((request[3] * request[2] / request[4]) + (i * step), lastStep, request[2]);
                    }
                    results.add(new FutureTask<>(result));
                    Thread t = new Thread(results.get(i));
                    t.start();
                }
                //Reduce the results of current worker(each instance of a serverThread is a worker that spawns new Threads.
                Double response = results.stream().map(ServerThread::getResult).mapToDouble(Double::new).sum();
                System.out.println(response);
                //send worker results back to master
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
