/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator.master;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author ptzafos
 */
public class ParallelPiCalculatorMaster {

    /**
     * @param args the command line arguments
     */

    private static final int[] PORT = {1208, 1209, 1210, 1211, 1212};

    public static void main(String args[]) {
        //send the steps to each worker
        Double piResult = 0.0;
        int N = 10000;
        int numberOfWorkers = 5;
        //5 workers 2000 each one;
        Socket taskSocket;
        ObjectOutputStream out;
        ArrayList<Socket> workerStreams = new ArrayList<>();
        try {
            for (int i = 0; i < PORT.length; i++) {
                int[] slice = {/*split slice*/i * N / numberOfWorkers,/*split slice*/ i * N / numberOfWorkers + N / numberOfWorkers,/*total split*/ N,/*worker number*/ i,/*total workers*/ numberOfWorkers};
                taskSocket = new Socket(InetAddress.getLocalHost(), PORT[i]);
                out = new ObjectOutputStream(taskSocket.getOutputStream());
                out.writeObject(slice);
                workerStreams.add(taskSocket);
            }
            for (Socket currSocket : workerStreams) {
                InputStream is = currSocket.getInputStream();
                BufferedReader in = new BufferedReader((new InputStreamReader(is)));
                Double replyResult = Double.valueOf(in.readLine());
                piResult += replyResult;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(piResult);
    }

}
