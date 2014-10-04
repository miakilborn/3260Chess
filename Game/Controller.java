package Game;
import java.util.*;
import java.net.*;
import java.io.*;
import RuleSets.*;
import Pieces.*;

public class Controller{
    public static final Boolean MASTER = true;
    public static final Boolean SLAVE = false;
    public static final int PORT = 5001;

    private String test;
    private Boolean mode;
    private PrintWriter socketOut;
    private BufferedReader socketIn;
    private ServerSocket masterPipe;
    private Socket slavePipe;
    private ControllerListener listener;
    private IRuleSet rules;
    private IBoard board;


    /* Default constructor
    *  @author bill
    */
    public Controller(){
        masterPipe = null;
        slavePipe = new Socket();
        InetSocketAddress address = new InetSocketAddress("127.0.0.1",PORT);

        //Set up connection
        try{
            masterPipe = new ServerSocket(address.getPort());
            mode = MASTER;
            System.out.println("You are player 1, waiting for player 2 to start the game...");
            slavePipe = masterPipe.accept();
        }
        catch(IOException e){
            //Address already in use. Connect to it.
            try{
                System.out.println("You are player 2, connecting to player 1...");
                slavePipe.connect(address);
                mode = SLAVE;
            }
            catch(IOException ioe){
                System.err.println("Could not establish a connection");
                System.exit(1);
            }
        }
        catch(Exception e){
            System.err.println("Could not establish a connection:"+e.toString());
            System.exit(1);
        }

        //Set up stream
        try{
            socketOut = new PrintWriter(slavePipe.getOutputStream(), true);
            socketIn = new BufferedReader(new InputStreamReader(slavePipe.getInputStream()));
        }
        catch(IOException ioe){
            System.err.println("Could not set up stream: "+ioe.toString());
            System.exit(1);
        }

        if(this.isMaster()){
            board = new Standard8x8Board();
            rules = new Standard8x8();
            rules.setupBoard(board);

            listener = new ControllerListener(this);
            listener.start();
        }
    }


    /* Send a command or a response over the socket
    *  @author bill
    */
    public void send(String term){
        socketOut.println(term);
    }

    /* Read from the socket to see if there is a command or a response there.
    *  @author bill
    */
    public String read(){
        if(slavePipe.isConnected()){
            try{
                //Thread.sleep(100);
                return socketIn.readLine();
            }
            catch(IOException e){
                System.err.println("Got nothing!"+e.toString());
                return "";
            }
        }
        else{
            System.err.println("Socket docsonnect");
            System.exit(1);
        }
        return "SOCKET DISCONNECT";
    }

    /* Methods to see if a controller is in master mode
    *  @author bill
    */
    public Boolean isMaster(){
        return mode;
    }

    /* Methods to see if a controller is in slave mode
    *  @author bill
    */
    public Boolean isSlave(){
        return !mode;
    }

    /* makeMove will be called by the player class and handled by the master controller
    *  @author bill
    *
    */
    public Boolean makeMove(Move m){
        if(this.isMaster()){
            if(rules.checkMove(this.board,m)){
                board.makeMove(m);
                return true;
            }
            return false;
        }
        else{
            this.send("makeMove|"+m.toString());
            String ret = this.read();
            return Boolean.getBoolean(ret);
        }
    }

    /* Safely shutdown the socket
    *  @author bill
    */
    public void shutdown(){
        try{
            this.slavePipe.shutdownInput();
            this.slavePipe.shutdownOutput();
            if(this.isMaster()){
                listener.join();
            }
        }
        catch(IOException e){
            System.err.println("Could not shut down cleanly!");
        }
        catch(InterruptedException e){
            System.err.println("Could not shut down cleanly!");
        };
    }

    /*
    * This is just for testing... the actual main will be in player.
    *
    */
    public static void main(String []args) throws InterruptedException{
        Controller me = new Controller();
        Scanner keyboard = new Scanner(System.in);

        if(me.isMaster()){
            Boolean ret = me.makeMove(new Move(me.board.getPieceAtPosition(new Coordinate(1,2)),new Coordinate(1,4)));
            System.out.println("Master Move Response: "+ret);
        }
        else if(me.isSlave()){
            Boolean ret = me.makeMove(new Move("M(Pawn(C(1,7),Black,false),C(1,6))"));
            System.out.println("Slave Move Response: "+ret);
        }
        else{
            System.err.println("An error occurred");
        }
        me.shutdown();
        System.exit(0);
    }


    /* Private class used by the controller to make a thread for the server
    *  @author bill
    *
    */
    private static class ControllerListener extends Thread{
        Controller c;

        public ControllerListener(Controller c){
            this.c = c;
        }

        public void run(){ //Thread that watches for commands from the second controller
            //TODO: Make this work with any number of arguments... may have to build objects in here dynamically

            while(c.isMaster()){
                try{
                    String get = c.read();
                    String [] cmd = get.split("\\|");

                    try{
                        //Call stuff
                        if(cmd[0].equals("makeMove")){
                            c.makeMove(new Move(cmd[1]));
                        }
                    }
                    catch(Exception e){
                        System.err.println(e.toString()+"--->"+get);
                    }
                }
                catch(NullPointerException e){
                    try{
                        Thread.sleep(100);
                    }
                    catch(InterruptedException ie){
                        //No Messages
                        System.out.println("Interrupted");
                    }
                }
            }
        }
    }
}
