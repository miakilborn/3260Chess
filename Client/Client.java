/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;
import Game.*;
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

        public void makeMoveIfApplicable(){
            if(this.client.role.equals("PLAYER")){
                Move move = this.client.view.getMove();
                if(move!=null){
                    this.send("MV|"+move.toString());
                }
            }
        }

        public void run(){
            String line;
            String array[] = new String[1];
            while(true){
                try{
                    Thread.sleep(5);
                }
                catch(InterruptedException ie){
                    System.err.println(ie.toString());
                }
                line = this.read();
                if(line == null){
                    continue;
                }
                System.err.println("RECIEVED COMMAND: "+line);
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
                        this.client.view.update = true;
                        try {
                            this.client.view.displayBoard(new Standard8x8Board(array[1]));
                        } catch (Exception ignored){
                            System.out.println(array[1]);
                        }
                        this.client.view.update = false;
                    break;
                    case "MSG":
                        this.client.view.displayMessage(array[1]);
                        break;
                    case "ROLE":
                        this.client.role=array[1];
                        break;
                    case "COLOUR":
                        this.client.view.setPlayerColour(array[1]);
                        break;
                    case "PROMPT":
                        this.client.view.displayMessage(array[1]);
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
    public String role;

    public void go(){
        String pattern = "[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}";
        String input;

        System.out.println ("Please enter the ip address of the server you would like to connect to.");
        input = "127.0.0.1";
        //input = keyboard.nextLine();
        while(!input.matches(pattern)){
            System.out.println("Please enter a valid IP address!");
            input = keyboard.nextLine();
        }

        Connect c = new Connect(input,this);
        c.send("RDY");
        c.send("ROOMS");
        String rooms = c.read();
        String temp[] = rooms.split("[{:,}]");
        ArrayList<String> room = new ArrayList<String>();

        int i = 0;
        for (String r : temp){
            if(!r.matches(".*\\w.*")){
               continue;
            }
            if(i%2 == 1) {
                room.add(r);
            }
            i++;
        }

        for (i = 0; i < room.size(); i++){
            System.out.print(room.get(i));
            if(i%2 == 1){
                System.out.print("\n");
            } else{
                System.out.print(": ");
            }

        }

        System.out.println("Which room would you like to join?");
        c.send("JOIN|" + keyboard.nextLine());
        c.start();
        while(true){
            Move move = this.view.getMove();
            System.err.println("Sending server move: " + move);
            c.send("MV|"+move);
        }
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
