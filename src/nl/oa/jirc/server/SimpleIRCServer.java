/**
 * 
 */
package nl.oa.jirc.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Marc de Kwant
 *
 */
public class SimpleIRCServer {
    public static void main(String[] args) throws IOException, InterruptedException {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(4444);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4444.");
            System.exit(1);
        }

        Socket clientSocket = null;
        boolean running = true;
        while (running) {
        
        
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }

        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                clientSocket.getInputStream()));
        String inputLine;
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        
        while ((inputLine = in.readLine()) != null) {
            if (inputLine.indexOf("quit")!=-1) {
               running=false;
               break;
            } else {
                // Echo the message
                out.println("privmsg raven1234 Message recieved..");
            }
        }
        out.close();
        in.close();
        
        }
        clientSocket.close();
        serverSocket.close();
        
        
    }
}
