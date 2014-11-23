/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;
import Game.Standard8x8Board;
import java.util.*;
import java.io.*;
import java.net.*;

/**
 *
 * @author Tim
 */
public class Client {

    private class Connect extends Thread{
        int id;
        PrintWriter out;
        BufferedReader in;
        public boolean ready=false;
        Client client;

        public void send(String s){
            while(!ready);
            out.write(s+"\n");
            out.flush();
        }

        public String read(){
            try{
                return in.readLine();
            }
            catch(IOException e){
                System.err.println("Got nothing!"+e.toString());
                System.exit(1);
                return null;
            }
        }

        public void run(){
            String line;
            String array[] = new String[1];
            while(true){
                line = this.read();
                if(-1 != line.indexOf("|")){
                    array = line.split("\\|",2);
                }
                else{
                    array[0] = line;
                }

                switch(array[0]){
                    case "NRDY":
                        this.ready=false;
                    break;
                    case "RDY":
                        this.ready=true;
                    break;
                    case "UP":
                        System.out.println("BOARD: "+array[1]);
                        this.client.view.displayBoard(new Standard8x8Board(array[1]));
                    break;
                    case "MSG":
                        this.client.view.displayMessage(array[1]);
                    break;
                    case "Q":
                        this.send("A|"+this.client.view.question(array[1]));
                    default:
                    System.err.println("Unknown command: "+array[0]);
                    break;
                }
            }
        }

        public Connect(String ip, Client c){
            Socket s = null;
            try{
                s = new Socket(ip,5001);
            }
            catch (Exception uhe){
                System.err.println(uhe.toString());
            }
            try{
                this.in=new BufferedReader(new InputStreamReader(s.getInputStream()));
                this.out=new PrintWriter(s.getOutputStream());
                ready=true;
            }
            catch(IOException ioe){
                System.err.println(ioe.toString());
            }
            this.id = id;
            this.client = c;
        }
    }

    //End Private Connect Class

    public TextView view;
    public Scanner keyboard;
    public void go(){
        String pattern = "[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}";
        String input;

        System.out.println ("Please enter the ip address of the server you would like to connect to.");
        input = keyboard.nextLine();
        while(!input.matches(pattern)){
            System.out.println("Please enter a valid IP address!");
            input = keyboard.nextLine();
        }

        Connect c = new Connect(input,this);
        c.send("RDY");
        c.send("ROOMS");
        String rooms = c.read();
        System.out.println(rooms);
        String room[] = rooms.split("[{:,}]");
        for(int i = 2; i < room.length; i+=2){
            System.out.print(room[i]);
            if(i%4==0){
                System.out.println();
            }
            else{
                System.out.print(": ");
            }
        }
        System.out.println("Which room would you like to join?");
        c.send("JOIN|"+keyboard.nextLine());
        c.start();
    }

    public Client(){
        keyboard = new Scanner(System.in);
        view = new TextView();
    }
    public static void main(String []args) throws InterruptedException, FileNotFoundException{
        File file = new File("log_client.txt");
        FileOutputStream fos = new FileOutputStream(file);
        PrintStream ps = new PrintStream(fos);
        System.setErr(ps);
        Client c = new Client();
        c.go();
    }
}
