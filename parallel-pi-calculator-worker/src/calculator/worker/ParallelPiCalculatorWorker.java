/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator.worker;

import java.net.ServerSocket;
import java.util.ArrayList;

/**
 *
 * @author ptzafos
 */
public class ParallelPiCalculatorWorker {

	/**
	 * @param args the command line arguments
	 */
	private static final int[] PORT = {1208, 1209, 1210, 1211, 1212};

	public static void main(String[] args) {
		ArrayList<ServerSocket> connectionSocketList = new ArrayList<>();
		try {
			System.out.println("Worker cluster is starting...");
			for (int i = 0; i < PORT.length; i++) {
				connectionSocketList.add(new ServerSocket(PORT[i]));
				Thread server = new Thread(new ServerThread(connectionSocketList.get(i)));
				server.start();
			}
			System.out.println("Worker \"cluster\" is up and running...");
		} catch (Exception exc) {
			System.out.println("Error starting server in port " + PORT);
			exc.printStackTrace();
		}
	}

}
