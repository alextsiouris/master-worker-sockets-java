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
        int[] slice = {1, 2, 3, 4, 6, 7};
        Socket echoSocket;
        ObjectOutputStream out;
        try {
            echoSocket = new Socket(InetAddress.getLocalHost(), PORT[2]);
            out = new ObjectOutputStream(echoSocket.getOutputStream());
            out.writeObject(slice);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
