/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.*;
import java.util.*;

/**
 *
 * @author Tim
 */
public class Server {
    private ArrayList<GameRoom> rooms;

    private class ClientConnect implements Observer{
        int id;
        Socket s;

        public ClientConnect(Socket s, int id){
            this.s=s;
            this.id=id;
        }
    }

    private class Connect extends Thread{
        ServerSocket socket;
        Server server;

        public void run(){

        }

        public Connect(ServerSocket s, Server ser){
            server = ser;
            socket = s;
        }
    }

    public static void main(String []args) throws InterruptedException, FileNotFoundException{
        File file = new File("log_server.txt");
        FileOutputStream fos = new FileOutputStream(file);
        PrintStream ps = new PrintStream(fos);
        System.setErr(ps);
    }
}
