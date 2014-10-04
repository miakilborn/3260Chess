package Game;
import java.util.*;
import java.net.*;
import java.io.*;
import java.lang.reflect.Method;
import RuleSets.Standard8x8;

public class Controller{
    public static final Boolean MASTER = true;
    public static final Boolean SLAVE = false;
    public static final int PORT = 5001;

    private String test;
    private Boolean mode;
    private PrintWriter socketOut;
    private BufferedReader socketIn;

    public Controller(String test){
            ServerSocket masterPipe = null;
            Socket slavePipe = new Socket();
            InetSocketAddress address = new InetSocketAddress("127.0.0.1",PORT);

            //Set up connection
            try{
                masterPipe = new ServerSocket(address.getPort());
                mode = MASTER;
                System.out.println("You are master, waiting for slave...");
                slavePipe = masterPipe.accept();
            }
            catch(IOException e){
                //Address already in use. Connect to it.
                try{
                    System.out.println("You are slave, connecting to master...");
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
    }

    public void send(String term){
        socketOut.println(term);
    }

    public String read(){
        try{
            return socketIn.readLine();
        }
        catch(IOException e){
            System.err.println("Got nothing!"+e.toString());
            return "";
        }
    }

    public Boolean isMaster(){
        return mode;
    }

    public Boolean isSlave(){
        return !mode;
    }

    public Boolean test(String example){
        System.out.println("TEST FUNCTION: "+example);
        return true;
    }

    public static void main(String []args) throws InterruptedException{
        Controller me = new Controller("");
        Scanner keyboard = new Scanner(System.in);

        if(me.isMaster()){
            me.send("RDY");
            String get = me.read();
            me.send("ACK:"+get);

            String [] cmd = get.split(",");

            try{
                Method method = me.getClass().getMethod(cmd[0],cmd[1].getClass());
                method.invoke(me,cmd[1]);
            }
            catch(Exception e){
                System.err.println(e.toString());
            }
        }
        else if(me.isSlave()){
            String got = me.read();
            if(got.equals("RDY")){
                String message = "test,from slave";
                me.send(message);
                if(me.read().equals("ACK:"+message)){
                    System.out.println("Master ACK... data valid");
                }
                else{
                    //Master got something else
                }
            }

        }
        else{
            System.err.println("An error occurred");
        }
        System.exit(0);
    }
}
