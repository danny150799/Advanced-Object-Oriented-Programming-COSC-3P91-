package Game;

import com.sun.security.ntlm.Server;

import javax.sound.midi.Soundbank;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.Scanner;

/**
 * Danny Nguyen, 6334502, dn17hg. I use IntelliJ.
 * This class controls the server side. It will set up the server socket and when a client connects to the server, it
 * will create a new thread to handle that client, thus support multi clients at the same time.
 */

public class GameEngine {
    private static final int PORT = 12345;
    public static void main(String[] args){
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connected to new client " + clientSocket);
                new PlayerControl(new Player(), new PlayerView(), clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    };
}
