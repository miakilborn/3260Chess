package Game;

import Game.*;
import Pieces.*;

public class Player {
    private static String name;
    private static String colour;
    private int score;

    private IInterface ui;
    private Controller controller;

    /**
     * Construct player with the players game name, and colour
     *
     */
    public Player(Controller controller){
        this.controller = controller;
        ui = new TextInterface();
        if(controller.isMaster()){
            colour = "White";
        } else {
            colour = "Black";
        }
        ui.setPlayerColour(colour);
        name = ui.getUsername();
    }

    /**
    * Main class, runs the program
    *
    *
    */
    public void game(){
        //display board
        ui.displayBoard(controller.getBoard());


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
            if(result && controller.isMaster()) {
                ui.displayMessage("Valid move!");
                ui.displayBoard(controller.getBoard());
            } else {
                ui.displayMessage("Unable to perform move.");
            }
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        //over = true;
    }


    /**
    *
    *
    */
    public void update(){
        ui.displayBoard(controller.getBoard());
        //ui.getMove();
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
