/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator.master;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

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
        int N = 10000;
        //5 workers 2000 each one;
        Socket echoSocket;
        ObjectOutputStream out;
        try {
            for (int i = 0; i < PORT.length; i++ ){
                int[] slice = {i * N/5, i * N/5 + N/5, N, i};
//                System.out.println(i * N/5 + "---" + (i * N/5 + N/5));
                echoSocket = new Socket(InetAddress.getLocalHost(), PORT[i]);
                out = new ObjectOutputStream(echoSocket.getOutputStream());
                out.writeObject(slice);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
