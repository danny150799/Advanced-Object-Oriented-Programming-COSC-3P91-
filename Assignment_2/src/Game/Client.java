package Game;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

/**
 * This class represents the client side. Here, the player will receive messages from the server and gives inputs
 * so that they can do things in game. Please wait 3 seconds of nothing appear immediately, the delay is there to make sure
 * the client side will receive every messages from the server before asking player for input.
 */
public class Client{
    private static final int PORT = 12345;
    private static final String HOST = "127.0.0.1";
    public static void main (String args[]) throws Exception{

        try(Socket clientSocket = new Socket(HOST, PORT);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))

            ){
            System.out.println("Connected");
            String guide = "-1";
            String command = null;
            while (true){
                if(guide.equals("10"))break;
                command = in.readLine();
                System.out.println(command);
                do {
                    while (in.ready()) {
                        command = in.readLine();
                        System.out.println(command);
                    }
                    TimeUnit.SECONDS.sleep(3);
                    if(!in.ready())break;
                }while(true);
                guide = stdIn.readLine();
                out.println(guide);

            }
            clientSocket.close();

        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }
}
