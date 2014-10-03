import java.util.*;
import java.net.*;
import java.io.*;

public class Controller{
    public static final Boolean MASTER = true;
    public static final Boolean SLAVE = false;
    public static final int PORT = 5001;

    private String test;
    private Boolean mode;
    private PrintWriter socketOut;
    private BufferedReader socketIn;

    public Controller(String test){
            ServerSocket serverPipe = null;
            Socket clientPipe = new Socket();
            InetSocketAddress address = new InetSocketAddress("127.0.0.1",PORT);

            //Set up connection
            try{
                serverPipe = new ServerSocket(address.getPort());
                mode = MASTER;
                System.out.println("You are master, waiting for slave...");
                clientPipe = serverPipe.accept();
            }
            catch(IOException e){
                //Address already in use. Connect to it.
                try{
                    System.out.println("You are slave, connecting to master...");
                    clientPipe.connect(address);
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
                socketOut = new PrintWriter(clientPipe.getOutputStream(), true);
                socketIn = new BufferedReader(new InputStreamReader(clientPipe.getInputStream()));
            }
            catch(IOException ioe){
                System.err.println("Could not set up stream: "+ioe.toString());
                System.exit(1);
            }
    }

    public void send(String term){
        socketOut.println(term);
    }

    public String recieve() throws IOException{
        return socketIn.readLine();
    }

    public Boolean isMaster(){
        return mode;
    }

    public Boolean isSlave(){
        return !mode;
    }

    public static void main(String []args){
        Controller me = new Controller("");
        Scanner input = new Scanner(System.in);

        if(me.isMaster()){
            me.send("Hello From Master");
            try{
                System.out.println(me.recieve());
            }
            catch(IOException e){
                System.err.println("Nothing recieved from master: "+e.toString());
            }
        }
        else if(me.isSlave()){
            try{
                System.out.println(me.recieve());
            }
            catch(IOException e){
                System.err.println("Nothing recieved from master: "+e.toString());
            }
            me.send("Hello from slave!");
        }
        else{
            System.err.println("An error occurred");
        }
        return;
    }
}
