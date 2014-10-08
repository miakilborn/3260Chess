package Game;

import Game.*;
import Pieces.*;

public class Player {
    private static String name;
    private static String colour;
    private int score;

    /**
    * Main class, runs the program
    *
    *
    */
    public static void main(String[] args){
        Controller controller = new Controller();
        IInterface ui = new TextInterface();

        //init
        if(controller.isMaster()){
            colour = "White";
        } else {
            colour = "Black";
        }

        ui.setPlayerColour(colour);
        name = ui.getUsername();

        //enter gameplay loop
        boolean over = false;
        while(!over){

            //display board
            ui.displayBoard(controller.getBoard().toString());


            //wait for turn


            //get move
            Move move = ui.getMove(controller.getBoard());

            while(move == null){
                ui.displayMessage("Unable to perform move.");
                move = ui.getMove(controller.getBoard());
            }

            //validate move
            boolean result = false;
            try{
                result = controller.makeMove(move);
                if(result) {
                    ui.displayMessage("Valid move!");
                    ui.displayBoard(controller.getBoard().toString());
                } else {
                    ui.displayMessage("Unable to perform move.");
                }
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }

            over = true;
        }
    }

    /**
     * Construct player with the players game name, and colour
     *
     */
    public Player(String name, String colour){
        this.name = name;
        this.colour = colour;
    }

    /**
     * Get the players game name
     *
     */
    public String getPlayerName(){
        return name;
    }

    /**
     * Get the players game colour
     *
     */
    public String getColour(){
        return colour;
    }

    /**
     * Get the players current game score
     *
     */
    public int getScore(){
        return score;
    }
}
