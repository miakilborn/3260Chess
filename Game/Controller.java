package Game;
import Rules.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller{
    public static final Boolean MASTER = true;
    public static final Boolean SLAVE = false;
    public static final int PORT = 5001;
    private static Boolean OTHER_RDY = false;
    private Result result = null; //Result from latest move

    private Boolean mode;
    private PrintWriter socketOut;
    private BufferedReader socketIn;
    private ServerSocket masterPipe;
    private Socket slavePipe;
    private ControllerListener listener;
    private Rules rules;
    private Board board;
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
            slavePipe.setKeepAlive(true);
            slavePipe.setTcpNoDelay(true);
        }
        catch(SocketException so){
            System.err.println("Socket tcpnodelay failed");
        }

        p = new Player(this);

        board = new Standard8x8Board();
        rules = new BasicRules(board);
        rules.setupBoard(board);

        listener = new ControllerListener(this);
        listener.start();

        this.send("RDY");        
    }
    
    public void start(){
        this.p.printResult(this.result);
        this.updateBoard();
        while(true){
            p.game();
        }
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
        if (result != null){
            if (result.isGameOver()){
                p.printEndGame(result);
                return true;
            }
        }
        
        p.updateDisplay();
        return true;
    }

    /**
     * Gets the desired optional rules from the master player
     * Note that the slave will see the board before the master has selected rules.
     * @author Mia
     */
    private void decorateRules(){
        if(this.isMaster()) {
            rules = p.decorateRules(rules);
        }
    }

//    public Result checkMove(Move m){
//        Result result = rules.checkMove(m);
//        this.result = result;
//        if(result.isValid()){
//            this.current_move = m;
//        }
//        return result;
//    }

    /* makeMove will be called by the player class and handled by the master controller
    *  @author bill
    *
    */
    public void makeMove(Move m) throws InterruptedException{
        while(!OTHER_RDY){
            System.out.println("Other isn't ready");
            Thread.sleep(100);
            System.err.println((this.isMaster()?"Master":"Slave") + " makeMove");
        }

        if(this.isMaster()){
            this.result = rules.makeMove(m);
            if (!m.getColour().equals(p.getColour())){
                this.sendResult(result);
                this.result = null;
            }
            this.sendBoard(board);
            updateBoard();
        }
        else{
            this.sendMove(m);
        }
    }
    
    public void sendResult(Result result)throws InterruptedException{
        while(!OTHER_RDY){Thread.sleep(100);System.err.println("sendResult");};
        if (result != null){
            this.send(result.toString());
        }
    }
    
    public void sendBoard(Board board)throws InterruptedException{
        while(!OTHER_RDY){Thread.sleep(100);System.err.println("sendBoard");};
        if (board != null){
            this.send("BD|"+board.toString());
        }
    }

    public void sendMove(Move move) throws InterruptedException{
        while(!OTHER_RDY){Thread.sleep(100);System.err.println("sendMove");};
        if (move != null){
            this.send("MV|"+move.toString());
        }
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
    public static void main(String []args) throws InterruptedException, FileNotFoundException{
        File file = new File("log.txt");
        FileOutputStream fos = new FileOutputStream(file);
        PrintStream ps = new PrintStream(fos);
        System.setErr(ps);
        
        Controller c = new Controller();
        c.decorateRules();
        c.updateBoard();
        c.start();
    }

    public Board getBoard(){
        return this.board;
    }
    
    
    public Result getResult(){
        Result result = null;
        if (this.result != null){
            result = new Result(this.result.isValid(), this.result.getMessage());
        }
        this.result = null;
        return result;
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

                    if(cmd[0].equals("RDY")) {
                        c.OTHER_RDY = true;
                    } else if(cmd[0].equals("N_RDY")) {
                        c.OTHER_RDY = false;
                    } else if(cmd[0].equals("MV")){
                        if (c.isMaster()) {
                            Move m = new Move(cmd[1]);
                            try {
                                c.makeMove(m);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        c.send("RDY");
                    } else if(cmd[0].equals("BD")) {
                        if (!c.isMaster() && cmd.length > 1) {
                            String boardStr = "";
                            for (int i = 1; i < cmd.length; i++) {
                                boardStr += cmd[i] + "|";
                            }
                            c.board = new Standard8x8Board(boardStr);
                            c.updateBoard();
                        }
                        c.send("RDY");
                    } else if(cmd[0].equals("ACK")) {
                        if (c.isMaster()) {
                            c.send("ACK");
                            c.send("RDY");
                        } else {
                            c.send("RDY");
                        }
                    } else if(cmd[0].equals("true")) {
                        c.result = new Result(true, Boolean.getBoolean(cmd[1]), cmd[2]);
                        c.send("RDY");
                    } else if(cmd[0].equals("false")) {
                        c.result = new Result(false, Boolean.getBoolean(cmd[1]), cmd[2]);
                        c.send("RDY");
                    }

                }
                catch(NullPointerException e){
                    try{
                        e.printStackTrace();
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