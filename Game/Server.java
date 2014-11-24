/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author Tim
 */
public class Server {
    public enum Role{
        PLAYER, SPECTATOR
    }

    private ArrayList<GameRoom> rooms;
    private ArrayList<ClientConnect> clients;

    private class ClientConnect extends Thread implements Observer {
        GameRoom room;
        int id;
        Role r;
        PrintWriter out;
        BufferedReader in;
        public boolean ready=false;
        private String answer;

        public void send(String s){
            try{
                while(!ready){Thread.sleep(5);}; //Only write if client is ready
            }
            catch(InterruptedException ie){
                System.err.println(ie.toString());
            }
            out.write(s+"\n");
            out.flush();
            System.err.println("SENDING COMMAND TO ID: "+this.id+", "+s);
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

        public void message(String m){
            this.send("MSG|"+m);
        }

        public String question(String q){
            this.answer = "";
            this.send("Q|"+q);
            while(this.answer.equals(""));
            return this.answer;
            //return this.read(); //This will block the process.
        }

        public void run(){
            System.err.println ("Thread started for client: "+this.id);
            String line;
            String array[] = new String[1];
            while(true){
                //Thread.sleep(10/);
                line = this.read();
                if(line == null){
                    continue;
                }
                System.err.println("RECIEVED COMMAND: "+line);
                if(-1 != line.indexOf("|")){
                    array = line.split("\\|");
                }
                else{
                    array[0] = line;
                }

                // System.out.println("ARRAY");
                // for (int i = 0; i < array.length; i++){
                //     System.out.println(i+": "+array[i]);
                // }
                // System.out.println("END ARRAY");

                switch(array[0]){
                    case "NRDY":
                        this.ready=false;
                        break;
                    case "RDY":
                        this.ready=true;
                        break;
                    case "PROMPT":
                        System.err.println("Prompt response recieved.");
                    case "MV":
                        if(this.r == Role.SPECTATOR){
                            this.send("MSG|You can not make moves, you are registered as a spectator.");
                            break;
                        }
                        else{
                            String move = "";
                            for (int i = 1; i < array.length ; i++){
                                move += array[i] + "|";
                            }
                            move = move.substring(0,move.length()-1);
                            Result res = this.room.rules.makeMove(new Move(move));
                            if(res.isValid()){
                                System.err.println("Valid move! Move made, updating observers");
                                this.room.setChanged();
                                this.room.notifyObservers(this.room.board);
                                if (res.getMessage().contains("PROMPT")){
                                    this.send(res.getMessage());
                                }
                            }
                            else{
                                System.err.println("Invalid move. Notifying player.");
                                this.send("MSG|Move could not be made: "+res.getMessage());
                            }
                        }

                        /*
                            Check that client is player,
                            Check that move is valid,
                            If Valid:
                                Make Move,
                                Update Gameroom
                            If Invalid
                                Send MSG to client
                            END
                        */
                        break;
                    case "ROOMS":
                        String roomString = "";
                        for (int i = 0; i < rooms.size();i++){
                            roomString += "{\"id\":"+i+",\"description\":\""+rooms.get(i).toString()+"\"}";
                            if(i<rooms.size()-1){
                                roomString += ",";
                            }
                        }
                        this.send(roomString);
                        break;
                    case "JOIN":
                        this.room = rooms.get(Integer.parseInt(array[1]));
                        this.room.addObserver(this);
                        if(this.room.numPlayers < 2){
                            this.r = Role.PLAYER;
                            this.send("ROLE|PLAYER");
                            this.send("COLOUR|"+(this.id==0?"White":"Black"));
                        }
                        else{
                            this.r = Role.SPECTATOR;
                            this.send("ROLE|SPECTATOR");
                        }
                        this.room.numPlayers++ ;
                        this.update(this.room,this.room.board);
                        //this.update(this.room,this.room.board.toString());
                        break;                        
                    default:
                        System.err.println("Unknown command: "+array[0]);
                        break;
                }
            }
        }

        public void update(Observable o, Object obj){
            this.send("UP|"+obj.toString());
        }

        public ClientConnect(Socket s, int id){
            try{
                s.setKeepAlive(true);
                s.setTcpNoDelay(true);
                this.in=new BufferedReader(new InputStreamReader(s.getInputStream()));
                this.out=new PrintWriter(s.getOutputStream());
                ready=true;
            }
            catch(IOException ioe){
                System.err.println(ioe.toString());
            }

            this.id=id;
        }
    }

    public Server(){
        ServerSocket socket=null;
        rooms = new ArrayList<GameRoom>();
        rooms.add(new GameRoom());
        clients = new ArrayList<ClientConnect>();
        try{
            socket = new ServerSocket(5001);
        }
        catch(IOException ioe){
            System.err.println(ioe.toString());
        }

        while(true){
            try{
                System.err.println("Listening on port 5001");
                ClientConnect newClient = new ClientConnect(socket.accept(), clients.size());
                clients.add(newClient);
                System.err.println("New Client Connected");
                newClient.start();
            }
            catch(IOException ioe){
                System.out.println(ioe.toString());
            }
        }
    }

    public static void main(String []args) throws InterruptedException, FileNotFoundException{
        File file = new File("log_server.txt");
        FileOutputStream fos = new FileOutputStream(file);
        PrintStream ps = new PrintStream(fos);
        System.setErr(ps);

        new Server();
    }
}
