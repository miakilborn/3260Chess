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
    private static Boolean OTHER_RDY = false;
    private static Move current_move = null;


    private String test;
    private Boolean mode;
    private PrintWriter socketOut;
    private BufferedReader socketIn;
    private ServerSocket masterPipe;
    private Socket slavePipe;
    private ControllerListener listener;
    private IRuleSet rules;
    private IBoard board;
    private Player p;


    /* Default constructor
    *  @author bill
    */
    public Controller(){
        masterPipe = null;
        slavePipe = new Socket();
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", PORT);

        //Set up connection
        try{
            masterPipe = new ServerSocket(address.getPort());
            mode = MASTER;
            System.out.println("You are player 1, waiting for player 2 to start the game...");
            slavePipe = masterPipe.accept();
            masterPipe.close();
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
            socketOut = new PrintWriter(slavePipe.getOutputStream());
            socketIn = new BufferedReader(new InputStreamReader(slavePipe.getInputStream()));
        }
        catch(IOException ioe){
            System.err.println("Could not set up stream: "+ioe.toString());
            System.exit(1);
        }

        try{
            slavePipe.setTcpNoDelay(true);
        }
        catch(SocketException so){
            System.err.println("Socket tcpnodelay failed");
        }

        p = new Player(this);

        board = new Standard8x8Board();
        rules = new Standard8x8();
        rules.setupBoard(board);

        listener = new ControllerListener(this);
        listener.start();

        this.send("RDY");
        p.game();
    }


    /* Send a command or a response over the socket
    *  @author bill
    */
    public void send(String term){
        socketOut.write(term+"\n");
        socketOut.flush();
        if(socketOut.checkError()){
            System.err.println("SHIIIIIIIT");
        }
    }

    /* Read from the socket to see if there is a command or a response there.
    *  @author bill
    */
    public String read(){
        if(slavePipe.isConnected()){
            try{
                return socketIn.readLine();
            }
            catch(IOException e){
                System.err.println("Got nothing!"+e.toString());
                System.exit(1);
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

    public Boolean updateBoard(){
        if(null==current_move){
            return false;
        }
        board.makeMove(current_move);
        current_move = null;
        p.update();
        return true;
    }

    public Boolean checkMove(Move m){
        if(this.rules.checkMove(this.board,m)){
            this.current_move = m;
            return true;
        }
        return false;
    }

    /* makeMove will be called by the player class and handled by the master controller
    *  @author bill
    *
    */
    public Boolean makeMove(Move m) throws InterruptedException{
        while(!OTHER_RDY){Thread.sleep(100);System.err.println((this.isMaster()?"Master":"Slave") + " makeMove");};
        if(this.isMaster()){
            if(rules.checkMove(this.board,m)){
                this.sendMove(m);
                board.makeMove(m);
                return true;
            }
            else{
                return false;
            }
        }
        else{
            current_move = m;
            this.sendMove(m);
            Thread.sleep(100);
            return Boolean.getBoolean(this.read());
        }
    }

    public void sendMove(Move m) throws InterruptedException{
        while(!OTHER_RDY){Thread.sleep(100);System.err.println("sendMove");};
        this.send("N_RDY");
        this.send("MV|"+m.toString());
    }

    /* Safely shutdown the socket
    *  @author bill
    */
    public void shutdown(){
        try{
            this.slavePipe.shutdownInput();
            this.slavePipe.shutdownOutput();
            listener.join();
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
    }


    public IBoard getBoard(){
        return this.board;
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

            while(true){
                try{
                    String get = c.read();
                    String [] cmd = get.split("\\|");
                    String role = c.isMaster()?"Master":"Slave";

                    System.err.println(role+" got: "+cmd[0]);

                    switch(cmd[0]){
                        case "RDY":
                            c.OTHER_RDY = true;
                            break;
                        case "N_RDY":
                            c.OTHER_RDY = false;
                            break;
                        case "MV":
                            if(c.isMaster()){
                                Boolean ret = c.checkMove(new Move(cmd[1]));
                                c.send(ret.toString());
                            }
                            else{
                                c.current_move = new Move(cmd[1]);
                                c.updateBoard();
                                System.err.println(role+" should send ACK");
                                c.send("ACK");
                            }
                            break;
                        case "ACK":
                            if(c.isMaster()){
                                if(c.updateBoard()){
                                    System.err.println(role+" should send ACK");
                                    c.send("ACK");
                                }
                                System.err.println(role+" should send RDY");
                                c.send("RDY");
                            }
                            else{
                                System.err.println(role+" should send RDY");
                                c.send("RDY");
                            }
                            break;
                        case "true":
                            c.updateBoard();
                            System.err.println(role+" should send ACK");
                            c.send("ACK");
                            break;
                        case "false":
                            System.err.println(role+" should send ACK");
                            c.send("ACK");
                            break;
                    }
                }
                catch(NullPointerException e){
                    try{
                        System.err.println("exception in read thread");
                        Thread.sleep(10);
                    }
                    catch(InterruptedException ie){
                        //No Messages
                        System.err.println("Interrupted");
                    }
                }
            }
        }
    }
}
